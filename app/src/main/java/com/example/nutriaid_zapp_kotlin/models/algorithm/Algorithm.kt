package com.example.nutriaid_zapp_kotlin.models.algorithm

import android.content.ContentValues.TAG
import android.util.Log
import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.api_services.SpoonacularService
import com.example.nutriaid_zapp_kotlin.models.full_recipe.RecipeFullData
import com.example.nutriaid_zapp_kotlin.models.interfaces.IAlgorithm
import com.example.nutriaid_zapp_kotlin.models.requests.FullRecipeParameters
import com.example.nutriaid_zapp_kotlin.models.short_recipe.RecipeShortData
import com.example.nutriaid_zapp_kotlin.models.short_recipe.ShortRecipe
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


class Algorithm(private val userSpecs: UserSpecs) : IAlgorithm {

    var recipeMap = hashMapOf<String, String>()

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
    var magnesiumPercent : Int = 0

    var minCalcium: String = "0"
    var totalCalcium: Int = 0
    var calciumPercent : Int = 0

    var minSodium: String = "0"
    var totalSodium: Int = 0
    var sodiumPercent : Int = 0

    var minPotassium: String = "0"
    var totalPotassium: Int = 0
    var potassiumPercent : Int = 0

    var minPhosporus: String = "0"
    var totalPhosphorus: Int = 0
    var phosphorusPercent : Int = 0

    var minC: String = "0"
    var totalC: Int = 0
    var cPercent : Int = 0

    var minE: String = "0"
    var totalE: Int = 0
    var ePercent : Int = 0

    var minK: String = "0"
    var totalK: Int = 0
    var kPercent : Int = 0

    var minA: String = "0"
    var totalA: Int = 0
    var aPercent : Int = 0

    var minB1: String = "0"
    var totalB1: Int = 0
    var b1Percent : Int = 0

    var minB2: String = "0"
    var totalB2: Int = 0
    var b2Percent : Int = 0

    var minB3: String = "0"
    var totalB3: Int = 0
    var b3Percent : Int = 0

    var minB5: String = "0"
    var totalB5: Int = 0
    var b5Percent : Int = 0

    var minB6: String = "0"
    var totalB6: Int = 0
    var b6Percent : Int = 0

    var minB12: String = "0"
    var totalB12: Int = 0
    var b12Percent : Int = 0

    override fun getRecipes(
        num: Int,
        firstRun: Boolean
    ) { //firstRun: For when an new User signed up -> no prior recipe data
        number = num.toString()
        for (i in userSpecs.dietExtras) {
            if (i == "high-protein")
                minProtein = "30"
            if (i == "low-calorie")
                maxCalories = "500"
            if (i == "high-calorie")
                minCalories = "700"
            if (i == "high-carb")
                minCarbs = "100"
            if (i == "low-fat")
                maxFat = "15"
        }

        //authentification
        val auth: FirebaseAuth = Firebase.auth
        val user = auth.currentUser
        val email = user?.email

        //reference
        val recipesRef: CollectionReference = FirebaseFirestore.getInstance().collection("recipes")

        if (!firstRun) { //if it is the first run, skip this

            //get recipes of user
            val list = mutableListOf<ShortRecipe>()
            val list2 = mutableListOf<RecipeFullData>()
            val query = recipesRef.whereEqualTo("email", email.toString()).get()
                .addOnSuccessListener {
                    if (it.isEmpty) {

                    }
                    for (doc in it) {
                        val temp2 = listOf<String>(
                            doc.data["calories"].toString(),
                            doc.data["carbs"].toString(),
                            doc.data["fat"].toString(),
                            doc.data["id"].toString(),
                            doc.data["image"].toString(),
                            doc.data["imageType"].toString(),
                            doc.data["protein"].toString(),
                            doc.data["title"].toString(),
                            doc.data["email"].toString()
                        )
                        val temp3 = ShortRecipe(temp2[0].toInt(), temp2[1], temp2[3], temp2[4].toInt(), temp2[5], temp2[6], temp2[7], temp2[9])
                        list.add(temp3)
                    }
                }

            var api: ApiRepository = ApiRepository(SpoonacularService.getInstance())
            for (i in list) {
                val x = FullRecipeParameters(i.id)
                var search = SearchParameters()
                var response = api.getFullRecipeById(x)

                response.enqueue(object : Callback<RecipeFullData> {
                    override fun onResponse(
                        call: Call<RecipeFullData>,
                        response: Response<RecipeFullData>
                    ) {
                        response.body()?.let { list2.add(it) }
                    }

                    override fun onFailure(call: Call<RecipeFullData>, t: Throwable) {}

                })
            }


            for (i in list2) {
                val size = i.nutrition.nutrients.size - 1
                for(j in 0..size){
                    if(i.nutrition.nutrients[j].name == "magnesium"){
                        magnesiumPercent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalMagnesium += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "calcium"){
                        calciumPercent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalCalcium += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "sodium"){
                        sodiumPercent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalSodium += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "potassium"){
                        potassiumPercent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalPotassium += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "phosphorus"){
                        phosphorusPercent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalPhosphorus += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminC"){
                        cPercent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalC += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminE"){
                        ePercent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalE += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminK"){
                        kPercent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalK += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminA"){
                        aPercent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalA += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminB1"){
                        b1Percent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalB1 += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminB2"){
                        b2Percent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalB2 += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminB3"){
                        b3Percent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalB3 += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminB5"){
                        b5Percent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalB5 += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminB6"){
                        b6Percent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalB6 += i.nutrition.nutrients[j].amount.toInt()
                    }
                    if(i.nutrition.nutrients[j].name == "vitaminB12"){
                        b12Percent += i.nutrition.nutrients[j].percentOfDailyNeeds.toInt()
                        totalB12 += i.nutrition.nutrients[j].amount.toInt()
                    }
                }
            }
            if (magnesiumPercent < 50 * 7) //average micro nutrient intake has to be above 50% of weekly(7 * daily) recommendation,
            //value in middle is the daily recommendation in mg/microg/ai (depends on spoonacular specifications)
                minMagnesium = (0.5 * 0.33 * ((totalMagnesium/magnesiumPercent)/7)).toString() //if micro nutrient is too low, minimum of 50% of daily recommendation
            // has to be reached(divided by 3 because of three recipes a day)
            if (totalCalcium < 50 * 7)
                minCalcium = (0.5 * 0.33 * ((totalCalcium/calciumPercent)/7)).toString()
            if (totalSodium < 50 * 7)
                minSodium = (0.5 * 0.33 * ((totalSodium/sodiumPercent)/7)).toString()
            if (totalPotassium < 50 * 7)
                minPotassium = (0.5 * 0.33 * ((totalPotassium/potassiumPercent)/7)).toString()
            if (totalPhosphorus < 50 * 7)
                minPhosporus = (0.5 * 0.33 * ((totalPhosphorus/phosphorusPercent)/7)).toString()
            if (totalC < 50 * 7)
                minC = (0.5 * 0.33 * ((totalC/cPercent)/7)).toString()
            if (totalE < 50 * 7)
                minE = (0.5 * 0.33 * ((totalE/ePercent)/7)).toString()
            if (totalK < 50 * 7)
                minK = (0.5 * 0.33 * ((totalK/kPercent)/7)).toString()
            if (totalA < 50 * 7)
                minA = (0.5 * 0.33 * ((totalA/aPercent)/7)).toString()
            if (totalB1 < 50 * 7)
                minB1 = (0.5 * 0.33 * ((totalB1/b1Percent)/7)).toString()
            if (totalB2 < 50 * 7)
                minB2 = (0.5 * 0.33 * ((totalB2/b2Percent)/7)).toString()
            if (totalB3 < 50 * 7)
                minB3 = (0.5 * 0.33 * ((totalB3/b3Percent)/7)).toString()
            if (totalB5 < 50 * 7)
                minB5 = (0.5 * 0.33 * ((totalB5/b5Percent)/7)).toString()
            if (totalB6 < 50 * 7)
                minB6 = (0.5 * 0.33 * ((totalB6/b6Percent)/7)).toString()
            if (userSpecs.diet != "vegan") //if the user is vegan, their normal diet wont contain
            //a lot of B12, therefore trying to find vegan recipes with high B12 content is relatively pointless,
            //maybe in this case the app can show a tip to supplement B12
                if (totalB12 < 50 * 7)
                    minB12 = (0.5 * 0.33 * ((totalB12/b12Percent)/7)).toString()
        }

        //delete recipes of the user from firebase before writing new ones
        val query2 = recipesRef.whereEqualTo("email", email.toString()).get()
            .addOnSuccessListener{
                for(doc in it){
                    doc.reference.delete()
                }
            }



        var api: ApiRepository = ApiRepository(SpoonacularService.getInstance())
        var search = SearchParameters(
            number = number,
            diet = userSpecs.diet,
            intolerances = userSpecs.intolerances,
            minCarbs = minCarbs,
            minProtein = minProtein,
            minCalories = minCalories,
            maxFat = maxFat,
            maxCalories = maxCalories,
            minMagnesium = minMagnesium,
            minCalcium = minCalcium,
            minSodium = minSodium,
            minPotassium = minPotassium,
            minPhosphorus = minPhosporus,
            minVitaminC = minC,
            minVitaminE = minE,
            minVitaminK = minK,
            minVitaminA = minA,
            minVitaminB1 = minB1,
            minVitaminB2 = minB2,
            minVitaminB3 = minB3,
            minVitaminB5 = minB5,
            minVitaminB6 = minB6,
            minVitaminB12 = minB12
        )
        var response = api.getListShortRecipes(search)

        //get recipes from spoonacular and write them to firebase
        response.enqueue(object : Callback<RecipeShortData> {
            override fun onResponse(
                call: Call<RecipeShortData>,
                response: Response<RecipeShortData>
            ) {
                val recipes = response.body()?.results //list<Typ shortRecipe>
                if (recipes != null) {
                    for (i in recipes) {
                        val temp = hashMapOf<String, String>(
                            "calories" to i.calories.toString(),
                            "carbs" to i.carbs,
                            "fat" to i.fat,
                            "id" to i.id.toString(),
                            "image" to i.image,
                            "imageType" to i.imageType,
                            "protein" to i.protein,
                            "title" to i.title,
                            "email" to email.toString()
                        )
                        recipesRef.add(temp).addOnSuccessListener { recipesRef ->
                            Log.d(TAG, "recipe added with ID: ${recipesRef.id}")
                        }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding recipe", e)
                            }
                    }
                }
            }

            override fun onFailure(call: Call<RecipeShortData>, t: Throwable) {}
        })
    }
}