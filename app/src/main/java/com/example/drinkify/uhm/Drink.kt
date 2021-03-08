package com.example.drinkify.uhm

import com.google.gson.annotations.SerializedName

class Drink {
    @SerializedName("idDrink")
     val idDrink: String? = null

    @SerializedName("strDrink")
     val strDrink: String? = null

    @SerializedName("strDrinkAlternate")
     val strDrinkAlternate: Any? = null

    @SerializedName("strDrinkES")
     val strDrinkES: Any? = null

    @SerializedName("strDrinkDE")
     val strDrinkDE: Any? = null

    @SerializedName("strDrinkFR")
     val strDrinkFR: Any? = null

    @SerializedName("strDrinkZH-HANS")
     val strDrinkZHHANS: Any? = null

    @SerializedName("strDrinkZH-HANT")
     val strDrinkZHHANT: Any? = null

    @SerializedName("strTags")
     val strTags: String? = null

    @SerializedName("strVideo")
     val strVideo: Any? = null

    @SerializedName("strCategory")
     val strCategory: String? = null

    @SerializedName("strIBA")
     val strIBA: String? = null

    @SerializedName("strAlcoholic")
     val strAlcoholic: String? = null

    @SerializedName("strGlass")
     val strGlass: String? = null

    @SerializedName("strInstructions")
     val strInstructions: String? = null

    @SerializedName("strInstructionsES")
     val strInstructionsES: Any? = null

    @SerializedName("strInstructionsDE")
     val strInstructionsDE: Any? = null

    @SerializedName("strInstructionsFR")
     val strInstructionsFR: Any? = null

    @SerializedName("strInstructionsZH-HANS")
     val strInstructionsZHHANS: Any? = null

    @SerializedName("strInstructionsZH-HANT")
     val strInstructionsZHHANT: Any? = null

    @SerializedName("strDrinkThumb")
     val strDrinkThumb: String? = null

    @SerializedName("strIngredient1")
     val strIngredient1: String? = null

    @SerializedName("strIngredient2")
     val strIngredient2: String? = null

    @SerializedName("strIngredient3")
     val strIngredient3: String? = null

    @SerializedName("strIngredient4")
     val strIngredient4: String? = null

    @SerializedName("strIngredient5")
     val strIngredient5: String? = null

    @SerializedName("strIngredient6")
     val strIngredient6: String? = null

    @SerializedName("strIngredient7")
     val strIngredient7: String? = null

    @SerializedName("strIngredient8")
     val strIngredient8: String? = null

    @SerializedName("strIngredient9")
     val strIngredient9: String? = null

    @SerializedName("strIngredient10")
     val strIngredient10: String? = null

    @SerializedName("strIngredient11")
     val strIngredient11: String? = null

    @SerializedName("strIngredient12")
     val strIngredient12: String? = null

    @SerializedName("strIngredient13")
     val strIngredient13: String? = null

    @SerializedName("strIngredient14")
     val strIngredient14: String? = null

    @SerializedName("strIngredient15")
     val strIngredient15: String? = null

    @SerializedName("strMeasure1")
     val strMeasure1: String? = null

    @SerializedName("strMeasure2")
     val strMeasure2: String? = null

    @SerializedName("strMeasure3")
     val strMeasure3: String? = null

    @SerializedName("strMeasure4")
     val strMeasure4: String? = null

    @SerializedName("strMeasure5")
     val strMeasure5: String? = null

    @SerializedName("strMeasure6")
     val strMeasure6: String? = null

    @SerializedName("strMeasure7")
     val strMeasure7: String? = null

    @SerializedName("strMeasure8")
     val strMeasure8: String? = null

    @SerializedName("strMeasure9")
     val strMeasure9: String? = null

    @SerializedName("strMeasure10")
     val strMeasure10: String? = null

    @SerializedName("strMeasure11")
     val strMeasure11: String? = null

    @SerializedName("strMeasure12")
     val strMeasure12: String? = null

    @SerializedName("strMeasure13")
     val strMeasure13: String? = null

    @SerializedName("strMeasure14")
     val strMeasure14: String? = null

    @SerializedName("strMeasure15")
     val strMeasure15: String? = null

    @SerializedName("dateModified")
     val dateModified: String? = null


    /*
    "idDrink": "11476",
      "strDrink": "Highland Fling Cocktail",
      "strDrinkAlternate": null,
      "strTags": null,
      "strVideo": null,
      "strCategory": "Ordinary Drink",
      "strIBA": null,
      "strAlcoholic": "Alcoholic",
      "strGlass": "Cocktail glass",
      "strInstructions": "Stir all ingredients (except olive) with ice and strain into a cocktail glass. Add the olive and serve.",
      "strInstructionsES": null,
      "strInstructionsDE": "Alle Zutaten (außer Oliven) mit Eis verrühren und in ein Cocktailglas abseihen. Die Olive dazugeben und servieren.",
      "strInstructionsFR": null,
      "strInstructionsIT": "Mescolare tutti gli ingredienti (eccetto l'oliva) con ghiaccio e filtrare in una coppetta da cocktail.Aggiungere l'oliva e servire.",
      "strInstructionsZH-HANS": null,
      "strInstructionsZH-HANT": null,
      "strDrinkThumb": "https://www.thecocktaildb.com/images/media/drink/0bkwca1492975553.jpg",
      "strIngredient1": "Scotch",
      "strIngredient2": "Sweet Vermouth",
      "strIngredient3": "Orange bitters",
      "strIngredient4": "Olive",
      "strIngredient5": null,
      "strIngredient6": null,
      "strIngredient7": null,
      "strIngredient8": null,
      "strIngredient9": null,
      "strIngredient10": null,
      "strIngredient11": null,
      "strIngredient12": null,
      "strIngredient13": null,
      "strIngredient14": null,
      "strIngredient15": null,
      "strMeasure1": "1 1/2 oz ",
      "strMeasure2": "3/4 oz ",
      "strMeasure3": "2 dashes ",
      "strMeasure4": "1 ",
      "strMeasure5": null,
      "strMeasure6": null,
      "strMeasure7": null,
      "strMeasure8": null,
      "strMeasure9": null,
      "strMeasure10": null,
      "strMeasure11": null,
      "strMeasure12": null,
      "strMeasure13": null,
      "strMeasure14": null,
      "strMeasure15": null,
      "strImageSource": null,
      "strImageAttribution": null,
      "strCreativeCommonsConfirmed": "No",
      "dateModified": "2017-04-23 20:25:53"
     */
}