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
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.analytics.client.logger.AnalyticsEventLogger;

import com.codenvy.cloudide.ext.minecraft.client.MineCraftResources;
import com.codenvy.cloudide.ext.minecraft.client.update.SetPropertyHandler;

import static com.codenvy.cloudide.ext.minecraft.shared.Properties.SAVE_MAP_PROPERTY_DEV_VALUE;
import static com.codenvy.cloudide.ext.minecraft.shared.Properties.SAVE_MAP_PROPERTY_NAME;

/**
 * @author Vladyslav Zhukovskyi
 */
@Singleton
public class SetSaveMapPropertyAction extends AbstractProjectAction {

    @Inject
    public SetSaveMapPropertyAction(MineCraftLocalization localization,
                                    MineCraftResources resources,
                                    SetPropertyHandler propertyHandler,
                                    AnalyticsEventLogger eventLogger) {
        super(localization.mineCraftSaveMapTitle(), localization.mineCraftSaveMapDescription(), resources.generic(), propertyHandler,
              eventLogger);
    }

    @Override
    String getPropertyName() {
        return SAVE_MAP_PROPERTY_NAME;
    }

    @Override
    String getPropertyValue() {
        return SAVE_MAP_PROPERTY_DEV_VALUE;
    }
}
