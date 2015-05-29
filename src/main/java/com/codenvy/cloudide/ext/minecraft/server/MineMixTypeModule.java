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
package com.codenvy.cloudide.ext.minecraft.server;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import org.eclipse.che.api.project.server.type.ProjectType;
import org.eclipse.che.inject.DynaModule;

/**
 * @author Vladyslav Zhukovskyi
 */
@DynaModule
public class MineMixTypeModule extends AbstractModule {
    @Override
    protected void configure() {
        final Multibinder<ProjectType> projectTypeMultibinder = Multibinder.newSetBinder(binder(), ProjectType.class);
        projectTypeMultibinder.addBinding().to(MineMixType.class);
    }
}
