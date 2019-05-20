package co.mewi.sample.valarmorghulis.model

class SampleResponses {

    val successThreeItemResponse = "{\n" +
            "\t\"celebrities\": {\n" +
            "\t\t\"Cersie\": {\n" +
            "\t\t\t\"height\": \"170\",\n" +
            "\t\t\t\"age\": 30,\n" +
            "\t\t\t\"popularity\": 70,\n" +
            "\t\t\t\"photo\": \"https://upload.wikimedia.org/wikipedia/en/thumb/f/f0/Jon_Snow-Kit_Harington.jpg/220px-Jon_Snow-Kit_Harington.jpg\"\n" +
            "\t\t},\n" +
            "\t\t\"Tyrion\": {\n" +
            "\t\t\t\"height\": \"140\",\n" +
            "\t\t\t\"age\": 35,\n" +
            "\t\t\t\"popularity\": 89,\n" +
            "\t\t\t\"photo\": \"https://wikiofthrones.com/static/uploads/2017/08/706-Dragonstone-Tyrion-2-768x511.jpg\"\n" +
            "\t\t},\n" +
            "\t\t\"Daenerys\": {\n" +
            "\t\t\t\"height\": \"160\",\n" +
            "\t\t\t\"age\": 32,\n" +
            "\t\t\t\"popularity\": 60,\n" +
            "\t\t\t\"photo\": \"https://img1.looper.com/img/gallery/predicting-daenerys-fate-in-the-final-season-of-game-of-thrones/intro-1548335649.jpg\"\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}"

    val firstItemObject = Character(
        "Cersie",
        "170",
        30,
        70,
        "https://upload.wikimedia.org/wikipedia/en/thumb/f/f0/Jon_Snow-Kit_Harington.jpg/220px-Jon_Snow-Kit_Harington.jpg"
    )

}