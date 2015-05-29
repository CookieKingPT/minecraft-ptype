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

import com.codenvy.cloudide.ext.minecraft.client.MineCraftLocalization;
import com.codenvy.cloudide.ext.minecraft.client.update.SetPropertyHandler;
import com.codenvy.cloudide.ext.minecraft.shared.Properties;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.analytics.client.logger.AnalyticsEventLogger;

import com.codenvy.cloudide.ext.minecraft.client.MineCraftResources;

/**
 * @author Vladyslav Zhukovskyi
 */
@Singleton
public class SetUpdatePropertyAction extends AbstractProjectAction {

    @Inject
    public SetUpdatePropertyAction(MineCraftLocalization localization,
                                   MineCraftResources resources,
                                   SetPropertyHandler propertyHandler,
                                   AnalyticsEventLogger eventLogger) {
        super(localization.mineCraftUpdateTitle(), localization.mineCraftUpdateDescription(), resources.generic(), propertyHandler, eventLogger);
    }

    @Override
    String getPropertyName() {
        return Properties.UPDATE_PROPERTY_NAME;
    }

    @Override
    String getPropertyValue() {
        return Properties.UPDATE_PROPERTY_DEF_VALUE;
    }
}
