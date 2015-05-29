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
package com.codenvy.cloudide.ext.minecraft.client.update;

import com.codenvy.cloudide.ext.minecraft.client.MineCraftLocalization;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.project.gwt.client.ProjectServiceClient;
import org.eclipse.che.api.project.shared.dto.ProjectDescriptor;
import org.eclipse.che.api.project.shared.dto.ProjectUpdate;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.app.CurrentProject;
import org.eclipse.che.ide.api.notification.Notification;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.dto.DtoFactory;
import org.eclipse.che.ide.rest.AsyncRequestCallback;
import org.eclipse.che.ide.rest.DtoUnmarshallerFactory;
import org.eclipse.che.ide.rest.Unmarshallable;

import java.util.Collections;

/**
 * @author Vladyslav Zhukovskyi
 */
@Singleton
public class SetPropertyHandler {

    private AppContext             appContext;
    private ProjectServiceClient   projectServiceClient;
    private DtoFactory             dtoFactory;
    private DtoUnmarshallerFactory dtoUnmarshallerFactory;
    private NotificationManager    notificationManager;
    private MineCraftLocalization  localization;

    @Inject
    public SetPropertyHandler(AppContext appContext,
                              ProjectServiceClient projectServiceClient,
                              DtoFactory dtoFactory,
                              DtoUnmarshallerFactory dtoUnmarshallerFactory,
                              NotificationManager notificationManager,
                              MineCraftLocalization localization) {
        this.appContext = appContext;
        this.projectServiceClient = projectServiceClient;
        this.dtoFactory = dtoFactory;
        this.dtoUnmarshallerFactory = dtoUnmarshallerFactory;
        this.notificationManager = notificationManager;
        this.localization = localization;
    }

    public void setProperty(String property, String value) {
        CurrentProject currentProject = appContext.getCurrentProject();

        if (currentProject == null) {
            return;
        }

        final Notification notification = new Notification(localization.mineCraftUpdateStarted(),
                                                           Notification.Type.INFO,
                                                           Notification.Status.PROGRESS);

        notificationManager.showNotification(notification);

        ProjectDescriptor projectDescription = currentProject.getProjectDescription();
        projectDescription.getAttributes().put(property, Collections.singletonList(value));

        ProjectUpdate projectUpdate = dtoFactory.createDto(ProjectUpdate.class)
                                                .withAttributes(projectDescription.getAttributes())
                                                .withBuilders(projectDescription.getBuilders())
                                                .withDescription(projectDescription.getDescription())
                                                .withMixinTypes(projectDescription.getMixins())
                                                .withRunners(projectDescription.getRunners())
                                                .withType(projectDescription.getType())
                                                .withVisibility(projectDescription.getVisibility());

        Unmarshallable<ProjectDescriptor> unmarshaller = dtoUnmarshallerFactory.newUnmarshaller(ProjectDescriptor.class);

        projectServiceClient.updateProject(projectDescription.getPath(),
                                           projectUpdate,
                                           new AsyncRequestCallback<ProjectDescriptor>(unmarshaller) {
                                               @Override
                                               protected void onSuccess(ProjectDescriptor result) {
                                                   notification.setMessage(localization.mineCraftUpdateFinished());
                                                   notification.setStatus(Notification.Status.FINISHED);
                                               }

                                               @Override
                                               protected void onFailure(Throwable exception) {
                                                   notification.setMessage(localization.mineCraftUpdateFailed());
                                                   notification.setStatus(Notification.Status.FINISHED);
                                               }
                                           });
    }
}
