package com.github.gaoyang.cs2ts.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.Messages
import com.intellij.psi.util.childrenOfType
import com.jetbrains.rdclient.util.idea.getLanguage
import com.jetbrains.rider.ideaInterop.fileTypes.csharp.CSharpLanguage
import com.jetbrains.rider.ideaInterop.fileTypes.csharp.psi.impl.CSharpDummyDeclaration

class ConvertToTypescriptAction : DumbAwareAction() {

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
        val project = e.getData(PlatformDataKeys.PROJECT)
        val editor = e.getData(PlatformDataKeys.EDITOR)
        val psiFile = e.getData(CommonDataKeys.PSI_FILE) ?: return
        var classList = psiFile.childrenOfType<CSharpDummyDeclaration>()
    }
}