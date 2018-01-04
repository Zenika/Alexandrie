package enzyme

import react.RBuilder
import react.buildElement

fun shallowElement(handler: RBuilder.() -> Unit) = shallow(buildElement(handler = handler))
