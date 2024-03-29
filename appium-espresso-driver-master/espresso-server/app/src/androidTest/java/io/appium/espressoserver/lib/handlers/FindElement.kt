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

import io.appium.espressoserver.lib.handlers.exceptions.*
import io.appium.espressoserver.lib.helpers.*
import io.appium.espressoserver.lib.model.BaseElement
import io.appium.espressoserver.lib.model.ComposeElement
import io.appium.espressoserver.lib.model.EspressoElement
import io.appium.espressoserver.lib.model.Locator
import io.appium.espressoserver.lib.viewaction.ViewGetter

class FindElement : RequestHandler<Locator, BaseElement> {

    override fun handleEspresso(params: Locator): BaseElement {
        val parentView = params.elementId?.let {
            ViewGetter().getView(EspressoElement.getViewInteractionById(it))
        }
        // Test the selector
        val viewState = ViewFinder.findBy(
            parentView,
            params.using ?: throw InvalidSelectorException("Locator strategy cannot be empty"),
            params.value ?: throw InvalidArgumentException()
        )
            ?: throw NoSuchElementException(
                String.format(
                    "Could not find espresso element with strategy %s and selector %s",
                    params.using, params.value
                )
            )

        // If we have a match, return success
        return EspressoElement(viewState)
    }

    override fun handleCompose(params: Locator): BaseElement {
        val nodeInteractions = toNodeInteractionsCollection(params)
        if (nodeInteractions.fetchSemanticsNodes(false).isEmpty()) throw NoSuchElementException(
            String.format(
                "Could not find a compose element with strategy '%s' and selector '%s'",
                params.using, params.value
            )
        )
        return ComposeElement(nodeInteractions[0])
    }
}
