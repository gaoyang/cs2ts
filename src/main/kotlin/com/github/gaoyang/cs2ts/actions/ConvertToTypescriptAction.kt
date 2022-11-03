package com.github.gaoyang.cs2ts.actions

import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.childrenOfType
import com.intellij.testFramework.LightVirtualFile
import com.jetbrains.rdclient.util.idea.getLanguage
import com.jetbrains.rider.ideaInterop.fileTypes.csharp.CSharpLanguage
import com.jetbrains.rider.ideaInterop.fileTypes.csharp.psi.impl.CSharpDummyDeclaration

class ConvertToTypescriptAction : DumbAwareAction() {
    private val myOrientation = 1

    override fun update(e: AnActionEvent) {
        super.update(e)
        val editor = e.getData(CommonDataKeys.EDITOR)
        if (editor == null || editor.getLanguage() != CSharpLanguage) {
            e.presentation.isVisible = false
            return
        }
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        e.presentation.isEnabled = psiFile != null && psiFile.childrenOfType<CSharpDummyDeclaration>().any()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT) ?: return
        val psiFile = e.getData(CommonDataKeys.PSI_FILE) ?: return
        val classList = psiFile.childrenOfType<CSharpDummyDeclaration>()
        var fileEditor = FileEditorManager.getInstance(project) as FileEditorManagerImpl
        val language = Language.findLanguageByID("TypeScript") ?: return
        var targetPsiFile = PsiFileFactory.getInstance(project).createFileFromText(language, "interface Dog {}")
        var targetFile = LightVirtualFile()
        targetFile.setContent(null, targetPsiFile.text, true)
        fileEditor.windows.first().split(myOrientation, true, targetFile, true)
    }
}