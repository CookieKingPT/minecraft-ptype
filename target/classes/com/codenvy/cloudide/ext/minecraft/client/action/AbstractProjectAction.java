/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.cloudide.ext.minecraft.client.action;

import com.codenvy.cloudide.ext.minecraft.client.update.SetPropertyHandler;

import org.eclipse.che.api.analytics.client.logger.AnalyticsEventLogger;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.action.ProjectAction;
import org.eclipse.che.ide.api.app.CurrentProject;
import org.vectomatic.dom.svg.ui.SVGResource;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vladyslav Zhukovskyi
 */
public abstract class AbstractProjectAction extends ProjectAction {

    private List<String> allowedProjectTypes = Arrays.asList("maven", "ant");
    private SetPropertyHandler   updateHandler;
    private AnalyticsEventLogger eventLogger;

    public AbstractProjectAction(String actionTitle,
                                 String actionDescription,
                                 SVGResource actionIcon,
                                 SetPropertyHandler propertyHandler,
                                 AnalyticsEventLogger eventLogger) {
        super(actionTitle, actionDescription, actionIcon);
        this.updateHandler = propertyHandler;
        this.eventLogger = eventLogger;
    }

    @Override
    protected void updateProjectAction(ActionEvent e) {
        CurrentProject currentProject = appContext.getCurrentProject();

        if (currentProject == null) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }

        final String sinkProjectType = currentProject.getProjectDescription().getType();

        e.getPresentation().setEnabledAndVisible(allowedProjectTypes.contains(sinkProjectType));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        eventLogger.log(this);
        updateHandler.setProperty(getPropertyName(), getPropertyValue());
    }

    abstract String getPropertyName();

    abstract String getPropertyValue();
}
