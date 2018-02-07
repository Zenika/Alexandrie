package enzyme

fun ShallowWrapper.findInput(name: String) = find("""input[name="$name"]""")

fun ShallowWrapper.changeTo(value: String) = simulate("change", object {
    val target = object {
        val value = value
    }
})
