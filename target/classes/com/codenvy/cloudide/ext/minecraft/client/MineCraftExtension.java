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
package com.codenvy.cloudide.ext.minecraft.client;

import com.codenvy.cloudide.ext.minecraft.client.action.SetSaveMapPropertyAction;
import com.codenvy.cloudide.ext.minecraft.shared.Properties;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.api.project.gwt.client.ProjectServiceClient;
import org.eclipse.che.api.project.shared.dto.ProjectDescriptor;
import org.eclipse.che.api.project.shared.dto.ProjectUpdate;
import org.eclipse.che.ide.api.action.ActionManager;
import org.eclipse.che.ide.api.action.DefaultActionGroup;
import org.eclipse.che.ide.api.action.IdeActions;
import org.eclipse.che.ide.api.constraints.Constraints;
import org.eclipse.che.ide.api.event.ProjectActionEvent;
import org.eclipse.che.ide.api.event.ProjectActionHandler;
import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.api.keybinding.KeyBuilder;
import org.eclipse.che.ide.dto.DtoFactory;

import com.codenvy.cloudide.ext.minecraft.client.action.SetUpdatePropertyAction;
import org.eclipse.che.ide.keybinding.KeyBindingManager;
import org.eclipse.che.ide.rest.AsyncRequestCallback;
import org.eclipse.che.ide.rest.DtoUnmarshallerFactory;
import org.eclipse.che.ide.rest.Unmarshallable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * @author Vladyslav Zhukovskyi
 */
@Singleton
@Extension(title = "Hot Deployer", version = "1.0.0")
public class MineCraftExtension implements ProjectActionHandler {
    public static final String HOT_DEPLOYER_MAIN_GROUP      = "MineCraftMainGroup";
    public static final String SET_UPDATE_PROPERTY_ACTION   = "SetUpdatePropertyAction";
    public static final String SET_SAVE_MAP_PROPERTY_ACTION = "SetSaveMapPropertyAction";

    private ProjectServiceClient   projectService;
    private DtoFactory             dtoFactory;
    private DtoUnmarshallerFactory dtoUnmarshallerFactory;

    @Inject
    public MineCraftExtension(@Nonnull final ActionManager actionManager,
                              @Nonnull final SetUpdatePropertyAction setUpdatePropertyAction,
                              @Nonnull final SetSaveMapPropertyAction setSaveMapPropertyAction,
                              @Nonnull final ProjectServiceClient projectService,
                              @Nonnull final DtoFactory dtoFactory,
                              @Nonnull final DtoUnmarshallerFactory dtoUnmarshallerFactory,
                              @Nonnull final EventBus eventBus,
                              @Nonnull final KeyBindingManager keyBindingManager) {
        this.projectService = projectService;
        this.dtoFactory = dtoFactory;
        this.dtoUnmarshallerFactory = dtoUnmarshallerFactory;

        actionManager.registerAction(SET_UPDATE_PROPERTY_ACTION, setUpdatePropertyAction);
        actionManager.registerAction(SET_SAVE_MAP_PROPERTY_ACTION, setSaveMapPropertyAction);

        final DefaultActionGroup updateGroup = new DefaultActionGroup(HOT_DEPLOYER_MAIN_GROUP, false, actionManager);
        updateGroup.add(setUpdatePropertyAction);
        updateGroup.add(setSaveMapPropertyAction);

        keyBindingManager.getGlobal().addKey(new KeyBuilder().action().charCode('u').build(), SET_UPDATE_PROPERTY_ACTION);
        keyBindingManager.getGlobal().addKey(new KeyBuilder().action().charCode('M').build(), SET_SAVE_MAP_PROPERTY_ACTION);

        DefaultActionGroup buildMenuActionGroup = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_BUILD);
        buildMenuActionGroup.add(updateGroup, Constraints.LAST);

        eventBus.addHandler(ProjectActionEvent.TYPE, this);
    }

    @Override
    public void onProjectOpened(ProjectActionEvent event) {
        initDeployExtension(event.getProject());
    }

    @Override
    public void onProjectClosing(ProjectActionEvent event) {
        //stub
    }

    @Override
    public void onProjectClosed(ProjectActionEvent event) {
        //stub
    }

    private void initDeployExtension(ProjectDescriptor project) {
        final List<String> projectMixins = project.getMixins();
        final Map<String, List<String>> projectAttributes = project.getAttributes();

        if (!projectMixins.contains(Properties.MIXIN_TYPE_ID)) {
            projectMixins.add(Properties.MIXIN_TYPE_ID);
        }

        projectAttributes.put(Properties.SAVE_MAP_PROPERTY_NAME, asList(Properties.SAVE_MAP_PROPERTY_DEV_VALUE));
        projectAttributes.put(Properties.UPDATE_PROPERTY_NAME, asList(Properties.UPDATE_PROPERTY_DEF_VALUE));

        final Unmarshallable<ProjectDescriptor> unmarshaller = dtoUnmarshallerFactory.newUnmarshaller(ProjectDescriptor.class);

        updateProjectAttributes(project, new AsyncRequestCallback<ProjectDescriptor>(unmarshaller) {
            @Override
            protected void onSuccess(final ProjectDescriptor projectDescriptor) {

            }

            @Override
            protected void onFailure(final Throwable exception) {

            }
        });
    }

    private void updateProjectAttributes(final ProjectDescriptor project, final AsyncRequestCallback<ProjectDescriptor> updateCallback) {
        final ProjectUpdate projectToUpdate = dtoFactory.createDto(ProjectUpdate.class);
        copyProjectInfo(project, projectToUpdate);

        projectService.updateProject(project.getPath(), projectToUpdate, updateCallback);
    }

    private void copyProjectInfo(final ProjectDescriptor projectDescriptor, final ProjectUpdate projectUpdate) {
        projectUpdate.setType(projectDescriptor.getType());
        projectUpdate.setDescription(projectDescriptor.getDescription());
        projectUpdate.setAttributes(projectDescriptor.getAttributes());
        projectUpdate.setRunners(projectDescriptor.getRunners());
        projectUpdate.setMixinTypes(projectDescriptor.getMixins());
        projectUpdate.setBuilders(projectDescriptor.getBuilders());
        projectUpdate.setVisibility(projectDescriptor.getVisibility());
    }
}
