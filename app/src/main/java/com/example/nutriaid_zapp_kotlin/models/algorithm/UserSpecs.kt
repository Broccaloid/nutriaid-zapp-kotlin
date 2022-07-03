package com.example.nutriaid_zapp_kotlin.models.algorithm

//these tell the algorithm for what to search
class UserSpecs (
    var diet : String? = null, //diets supported by spoonacular, if empty -> omnivore
    var dietExtras: String?,
    //list of diet extras:
    //-high-protein
    //-low-calorie
    //-high-calorie
    //-high-carb
    //-low-fat
    var intolerances : String? = "gluten" //intolerances supported by spoonacular
)




