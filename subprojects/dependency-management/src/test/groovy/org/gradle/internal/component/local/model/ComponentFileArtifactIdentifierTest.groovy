/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.component.local.model

import org.gradle.internal.component.model.DefaultIvyArtifactName
import org.gradle.util.Matchers
import spock.lang.Specification

class ComponentFileArtifactIdentifierTest extends Specification {
    def "has useful string representation"() {
        def componentId = new OpaqueComponentIdentifier("comp")

        expect:
        new ComponentFileArtifactIdentifier(componentId, new File("a/b/c/thing")).toString() == "thing (comp)"
        new ComponentFileArtifactIdentifier(componentId, new DefaultIvyArtifactName("thing", "", ""), new File("a/b/c/thing")).toString() == "thing (comp)"
    }

    def "identifiers are equal when backing files are equal"() {
        def componentId = new OpaqueComponentIdentifier("comp")
        def otherId = new OpaqueComponentIdentifier("comp 2")

        def id = new ComponentFileArtifactIdentifier(componentId, new File("one"))
        def sameId = new ComponentFileArtifactIdentifier(componentId, new File("one"))
        def sameFileDifferentDisplayName = new ComponentFileArtifactIdentifier(componentId, new DefaultIvyArtifactName("one", "", "", "classifier"), new File("one"))
        def differentComponent = new ComponentFileArtifactIdentifier(otherId, new File("one"))
        def differentFile = new ComponentFileArtifactIdentifier(componentId, new File("two"))
        def differentFileSameDisplayName = new ComponentFileArtifactIdentifier(componentId, new DefaultIvyArtifactName("one", "", ""), new File("two"))

        expect:
        Matchers.strictlyEquals(id, sameId)
        id != differentComponent
        id != differentFile
        id != differentFileSameDisplayName
        id != sameFileDifferentDisplayName
    }
}
