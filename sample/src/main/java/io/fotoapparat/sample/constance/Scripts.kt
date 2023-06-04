package io.fotoapparat.sample.constance

object Scripts {
    const val CAR_NAME_SCRIPT = "(function f() {return document.querySelector(\"." +
            "report-header__title > .js-refresh > .js-refresh__loaded\").innerText})()"
    const val GOVERNMENT_NUMBER_SCRIPT = "(function f() {return document.querySelector" +
            "(\"#head-identifiers > p > span\").innerText})()"
}