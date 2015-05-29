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

import com.google.gwt.i18n.client.Messages;

/**
 * @author Vladyslav Zhukovskyi
 */
public interface MineCraftLocalization extends Messages {
    @Key("minecraft.update.title")
    String mineCraftUpdateTitle();

    @Key("minecraft.update.description")
    String mineCraftUpdateDescription();

    @Key("minecraft.update.started")
    String mineCraftUpdateStarted();

    @Key("minecraft.update.finished")
    String mineCraftUpdateFinished();

    @Key("minecraft.update.failed")
    String mineCraftUpdateFailed();

    @Key("minecraft.save.map.title")
    String mineCraftSaveMapTitle();

    @Key("minecraft.save.map.description")
    String mineCraftSaveMapDescription();
}
