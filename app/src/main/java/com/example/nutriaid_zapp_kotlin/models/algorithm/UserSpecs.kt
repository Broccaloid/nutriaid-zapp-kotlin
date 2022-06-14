package com.example.nutriaid_zapp_kotlin.models.algorithm

//these tell the algorithm for what to search
class UserSpecs {
    var diet : String = "vegetarian" //diets supported by spoonacular, if empty -> omnivore
    var dietExtras = arrayListOf<String>("high-protein", "low-calorie")
    //list of diet extras:
    //-high-protein
    //-low-calorie
    //-high-calorie
    //-high-carb
    //-low fat
    var intolerances : String = "dairy" //intolerances supported by spoonacular
}





