<<<<<<< HEAD
const attachAddSkillEventListener = function() {
=======
const addIngredientEventListener = function() {
>>>>>>> master
    if (document.getElementById("add-ingredient-button") !== null) {
        let i = 0;
        document.getElementById("add-ingredient-button").addEventListener("click", function (e) {
            e.preventDefault();
            let newDiv = document.createElement("div");
            // and give it some content
<<<<<<< HEAD
            newDiv.innerHTML = `<input class = "form-control" type="text" id="ingredients-param-${i}" name="ingredient-param" placeholder="Please enter an ingredient.">`
            // add the text node to the newly created div
            document.querySelector("#ingredient-div").appendChild(newDiv);
            i++;
        })
    }
}
attachAddSkillEventListener();
=======
            newDiv.innerHTML = `<input class = "form-control" type="text" id="ingredients-param-${i}" name="ingredients" placeholder="Input Ingredient">`;
            // add the text node to the newly created div
            document.querySelector("#ingredients-div").appendChild(newDiv);
            i++;
        })
    }
};
>>>>>>> master
