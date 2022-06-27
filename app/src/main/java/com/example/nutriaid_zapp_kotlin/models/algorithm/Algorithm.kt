package com.example.nutriaid_zapp_kotlin.models.algorithm

import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.api_services.SpoonacularService
import com.example.nutriaid_zapp_kotlin.models.interfaces.IAlgorithm
import com.example.nutriaid_zapp_kotlin.models.short_recipe.RecipeShortData
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class Algorithm(private val userSpecs: UserSpecs) : IAlgorithm {
    var number: String = "3"
    var cuisine: String = "0" //for recipe-exploring
    var diet: String = "0" //for recipe-exploring
    var minCarbs: String = "0"
    var minProtein: String = "0"
    var minCalories: String = "0"
    var maxFat: String = "0"
    var maxCalories: String = "0"

    var minMagnesium: String = "0"
    var totalMagnesium: Int = 0

    var minCalcium: String = "0"
    var totalCalcium: Int = 0

    var minSodium: String = "0"
    var totalSodium: Int = 0

    var minPotassium: String = "0"
    var totalPotassium: Int = 0

    var minPhosporus: String = "0"
    var totalPhosphorus: Int = 0

    var minC: String = "0"
    var totalC: Int = 0

    var minD: String = "0"
    var totalD: Int = 0

    var minE: String = "0"
    var totalE: Int = 0

    var minK: String = "0"
    var totalK: Int = 0

    var minA: String = "0"
    var totalA: Int = 0

    var minB1: String = "0"
    var totalB1: Int = 0

    var minB2: String = "0"
    var totalB2: Int = 0

    var minB3: String = "0"
    var totalB3: Int = 0

    var minB5: String = "0"
    var totalB5: Int = 0

    var minB6: String = "0"
    var totalB6: Int = 0

    var minB12: String = "0"
    var totalB12: Int = 0

    override fun getRecipes(num : Int, firstRun : Boolean){ //firstRun: For when an new User signed up -> no prior recipe data
        number = num.toString()
        for (i in userSpecs.dietExtras){
            if(i == "high-protein")
                minProtein = "30"
            if(i == "low-calorie")
                maxCalories = "500"
            if(i == "high-calorie")
                minCalories = "700"
            if(i == "high-carb")
                minCarbs = "100"
            if(i == "low-fat")
                maxFat = "20"
        }

        if(!firstRun){ //if it is the first run, skip this

            //TODO: Get weeklyRecipes from Firebase
            lateinit var weeklyRecipes : ...

            for(i in weeklyRecipes){
                totalMagnesium += i.magnesium.toInt()
                totalCalcium += i.Calcium.toInt()
                totalSodium += i.Sodium.toInt()
                totalPotassium += i.Potassium.toInt()
                totalPhosphorus += i.Phosphorus.toInt()
                totalC += i.vitaminC.toInt()
                totalE += i.vitaminE.toInt()
                totalK += i.vitaminK.toInt()
                totalA += i.vitaminA.toInt()
                totalB1 += i.vitaminB1.toInt()
                totalB2 += i.vitaminB2.toInt()
                totalB3 += i.vitaminA.toInt()
                totalB5 += i.vitaminB5.toInt()
                totalB6 += i.vitaminB6.toInt()
                totalB12 += i.vitaminB12.toInt()
            }
            if(totalMagnesium < 0.5 * 350 * 7) //average micro nutrient intake has to be above 50% of weekly(7 * daily) recommendation,
                //value in middle is the daily recommendation in mg/microg/ai (depends on spoonacular specifications)
                minMagnesium = (0.5 * 0.33 * 350).toString() //if micro nutrient is too low, minimum of 50% of daily recommendation has to be reached(divided by 3 because of three recipes a day)
            if(totalCalcium < 0.5 * 1000 * 7)
                minCalcium = (0.5 * 0.33 * 1000).toString()
            if(totalSodium < 0.5 * 1500 * 7)
                minSodium = (0.5 * 0.33 * 1500).toString()
            if(totalPotassium < 0.5 * 4000 * 7)
                minPotassium = (0.5 * 0.33 * 4000).toString()
            if(totalPhosphorus < 0.5 * 700 * 7)
                minPhosporus = (0.5 * 0.33 * 700).toString()
            if(totalC < 0.5 * 110 * 7)
                minC = (0.5 * 0.33 * 110).toString()
            if(totalE < 0.5 * 13 * 7)
                minE = (0.5 * 0.33 * 13).toString()
            if(totalK < 0.5 * 70 * 7)
                minK= (0.5 * 0.33 * 70).toString()
            if(totalA < 0.5 * 3000 * 7)
                minA = (0.5 * 0.33 * 3000).toString()
            if(totalB1 < 0.5 * 1.2 * 7)
                minB1 = (0.5 * 0.33 * 1.2).toString()
            if(totalB2 < 0.5 * 1.4 * 7)
                minB2 = (0.5 * 0.33 * 1.4).toString()
            if(totalB3 < 0.5 * 13 * 7)
                minB3 = (0.5 * 0.33 * 13).toString()
            if(totalB5 < 0.5 * 5 * 7)
                minB5 = (0.5 * 0.33 * 5).toString()
            if(totalB6 < 0.5 * 1.6 * 7)
                minB6 = (0.5 * 0.33 * 1.6).toString()
            if(userSpecs.diet != "vegan") //if the user is vegan, their normal diet wont contain
                //a lot of B12, therefore trying to find vegan recipes with high B12 content is relatively pointless,
                //maybe in this case the app can show a tip to supplement B12
                if(totalB12 < 0.5 * 3)
                    minB12 = (0.5 * 0.33 * 3).toString()
        }


        var api : ApiRepository = ApiRepository(SpoonacularService.getInstance())
        var search = SearchParameters(number = number, diet = userSpecs.diet, intolerances = userSpecs.intolerances, minCarbs = minCarbs, minProtein = minProtein, minCalories = minCalories,
                                      maxFat = maxFat, maxCalories = maxCalories, minMagnesium = minMagnesium, minCalcium = minCalcium, minSodium = minSodium, minPotassium = minPotassium,
                                      minPhosphorus = minPhosporus, minVitaminC = minC, minVitaminE = minE, minVitaminK = minK, minVitaminA = minA, minVitaminB1 = minB1, minVitaminB2 = minB2,
                                      minVitaminB3 = minB3, minVitaminB5 = minB5, minVitaminB6 = minB6, minVitaminB12 = minB12)
        var response  = api.getListShortRecipes(search)

        response.enqueue(object : Callback<RecipeShortData> {
            override fun onResponse(call: Call<RecipeShortData>, response: Response<RecipeShortData>) {
                val recipes = response.body()?.results //list<Typ shortRecipe>
                if (recipes != null) {
                    for(i in recipes){
                        //TODO: Write i to firebase
                    }
                }
            }
            override fun onFailure(call: Call<RecipeShortData>, t: Throwable) {}
        })
    }





}