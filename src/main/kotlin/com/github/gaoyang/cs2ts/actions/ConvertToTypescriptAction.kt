package com.github.gaoyang.cs2ts.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.Messages
import com.jetbrains.rdclient.util.idea.getLanguage
import com.jetbrains.rider.ideaInterop.fileTypes.csharp.CSharpLanguage

class ConvertToTypescriptAction : DumbAwareAction() {

    override fun update(e: AnActionEvent) {
        super.update(e)
        val editor = e.getData(CommonDataKeys.EDITOR)
        val language = editor?.getLanguage()
        if (language != CSharpLanguage) {
            e.presentation.isVisible = false
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)
        Messages.showMessageDialog(project, "Hello from Test!", "TestTitle", Messages.getInformationIcon())
    }
}