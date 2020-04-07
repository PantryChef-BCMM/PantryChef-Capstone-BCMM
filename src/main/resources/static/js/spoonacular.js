//Code to get the ingredients from the user and put them in a list



// var client = spoonacular.init(document.querySelector('meta.sapi').content);


//function that puts the ingredient items into a array
$(document).ready(function () {


    let ingredientArray = [];

    $('#query-submit-btn').click(function (e) {

        e.preventDefault();
        let ingredientItem = $('#ingredient-query-input').val();

        ingredientArray.push(ingredientItem);

        // for (var i = 0; i < ingredientArray.length; i++) {
        //     alert(ingredientArray[i]);
        //     $("#ingredient-list-container").append('<li>' +  ingredientArray[i] + '</li>');
        // }

        console.log(ingredientArray);
        var recipeString = ingredientArray.join(",+");
        console.log(recipeString);

        var recipeCards = $.ajax("https://api.spoonacular.com/recipes/findByIngredients" + "?ingredients=" + recipeString + "&number=2&apiKey=0be955ec85264c56bd8649c5e1b7a666");

        recipeCards.done(function (data) {
            console.log(data)
        })
    });
});


