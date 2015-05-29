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


import com.codenvy.cloudide.ext.minecraft.shared.Properties;

import org.eclipse.che.api.project.server.type.ProjectType;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Vladyslav Zhukovskyi
 */
@Singleton
public class MineMixType extends ProjectType {
    @Inject
    public MineMixType() {
        super(Properties.MIXIN_TYPE_ID, Properties.MIXIN_TYPE_ID, false, true);

        addVariableDefinition(Properties.UPDATE_PROPERTY_NAME, "", false);
        addVariableDefinition(Properties.SAVE_MAP_PROPERTY_NAME, "", false);
    }
}
