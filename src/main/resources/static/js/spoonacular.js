"use strict";



let ingredientArray = [];
let recipeString;
let recipeId;


function recipeSubmitEventListener() {

    ingredientArray.push(document.getElementById("ingredient-query-input").value);
    // console.log(ingredientArray);
    document.getElementById("ingredient-query-input").value = "";
    recipeString = ingredientArray.join(",+");


}

const searchRecipeEventListener = function () {

    // console.log(recipeString);

    var recipeCards = $.ajax("https://api.spoonacular.com/recipes/findByIngredients" + "?ingredients=" + recipeString + "&number=100&apiKey=0be955ec85264c56bd8649c5e1b7a666");

    recipeCards.done(function (data) {
        // console.log(data);


        //Create the cards to hold the data for the recipe

        var recipe = "";

        // console.log(data[0].title);

        for (var i = 0; i < data.length; i++) {


            recipe += "<div class=card style=\"min-width: 18rem;\">";
            recipe += '<img class="card-img-top img-fluid" src="' + data[i].image + '" alt="" />';
            recipe += '<div class=card-body >';
            recipe += '<h5 class=card-title>' + data[i].title + '</h5>';
            recipe += '<button type="button" class="btn btn-primary recipe" data-toggle="modal" data-target="#recipeModalLong" id="' + data[i].id + '\">\n' + '  See Recipe Details\n' + '</button>'
            recipe += "</div>";
            recipe += "</div>";


        }

        $("#recipeCards").html(recipe);

        $('.recipe').on('click', function () {



            console.log(this.id);

            $.ajax("https://api.spoonacular.com/recipes/" + this.id + "/information?includeNutrition=false&apiKey=0be955ec85264c56bd8649c5e1b7a666").done(function (data) {

                console.log(data);

                //Variables to show the specific recipe details
                let recipeTitle ="";
                let recipeImage="";
                let recipeIngredients="";
                let recipeInstructions="";
                let recipeCuisines="";
                let recipeSource="";

                // recipeTitle += data.title + " by " + data.creditsText;
                recipeTitle += data.title + " by " + '<a href="'+ data.sourceUrl + '"target=_blank>' + data.creditsText + '</a>';


                recipeImage += '<img class="img-responsive" src="' + data.image + '" alt="" />';


                //Display the categories/cuisines for the choosen recipe
                if(data.cuisines.length == 0){

                    recipeCuisines +=  '<li>' + 'Sorry, no Cuisines associated with recipe at the moment.' + '</li>';

                }else{
                    for(var j = 0; j < data.cuisines.length; j++){

                        recipeCuisines += '<li>' + data.cuisines[j] + '</li>'

                    }
                }


                //For loop to grab the ingredients and put them in a list
                if(data.extendedIngredients.length == 0){

                    recipeIngredients += 'Currently no available ingredients for this recipe sorry.'

                }else{
                    for(var i = 0; i < data.extendedIngredients.length; i++){

                        recipeIngredients += '<li class="list-group-item">' +  data.extendedIngredients[i].amount + " " + data.extendedIngredients[i].unit + " " + data.extendedIngredients[i].originalName + '</li>';

                    }
                }


                //Grab the instructions from the get request of the the recipe
                if(data.analyzedInstructions.length == 0){

                    recipeInstructions += '<li class="list-group-item">' + 'Currently no available instructions for this recipe sorry.' + '</li>';

                }else{

                    for(var k = 0; k < data.analyzedInstructions[0].steps.length; k++){

                        recipeInstructions += '<li class="list-group-item">' + "Step " + data.analyzedInstructions[0].steps[k].number + ": " + data.analyzedInstructions[0].steps[k].step + '</li>';

                    }

                }


                //External link for further recipe details
                // recipeSource += '<a href="'+ data.sourceUrl + '"target=_blank>' + 'See Recipe Source' + '</a>';

                recipeSource += '<p>' +'Click on who its by in title to see more recipe details'+ '</p>';



                //using jquery to display the object properties from the get request
                $('.modal-title').html(recipeTitle);

                $('.modal-cuisine').html(recipeCuisines);

                $('.modal-image').html(recipeImage);

                $('.ingredient-list-group').html(recipeIngredients);

                $('.instruction-list-group').html(recipeInstructions);

                $('.modal-recipeSource').html(recipeSource);

            })
        })
    });
};


