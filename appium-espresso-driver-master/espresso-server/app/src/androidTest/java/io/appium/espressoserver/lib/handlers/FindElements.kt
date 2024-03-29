/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.espressoserver.lib.handlers

import io.appium.espressoserver.lib.handlers.exceptions.InvalidArgumentException
import io.appium.espressoserver.lib.handlers.exceptions.InvalidSelectorException
import io.appium.espressoserver.lib.helpers.*
import io.appium.espressoserver.lib.model.BaseElement
import io.appium.espressoserver.lib.model.ComposeElement
import io.appium.espressoserver.lib.model.EspressoElement
import io.appium.espressoserver.lib.model.Locator
import io.appium.espressoserver.lib.viewaction.ViewGetter

class FindElements : RequestHandler<Locator, List<BaseElement>> {

    override fun handleEspresso(params: Locator): List<BaseElement> {
        val parentView = params.elementId?.let {
            ViewGetter().getView(EspressoElement.getViewInteractionById(it))
        }

        // Return as list of Elements
        return ViewFinder.findAllBy(
            parentView,
            params.using ?: throw InvalidSelectorException("Locator strategy cannot be empty"),
            params.value ?: throw InvalidArgumentException()
        ).map { EspressoElement(it) }
    }

    override fun handleCompose(params: Locator): List<BaseElement> {
        val nodeInteractions = toNodeInteractionsCollection(params)
        return List(nodeInteractions.fetchSemanticsNodes(false).size)
            { index -> ComposeElement(nodeInteractions[index]) }
    }
}
