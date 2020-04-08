const attachAddSkillEventListener = function() {

    if (document.getElementById("add-ingredient-button") !== null) {
        let i = 0;
        document.getElementById("add-ingredient-button").addEventListener("click", function (e) {
            e.preventDefault();
            let newDiv = document.createElement("div");
            // and give it some content
            newDiv.innerHTML = `<input class = "form-control" type="text" id="ingredients-param-${i}" name="ingredient-param" placeholder="Please enter an ingredient.">`
            // add the text node to the newly created div
            document.querySelector("#ingredient-div").appendChild(newDiv);
            i++;
        })
    }
};
attachAddSkillEventListener();


const attachAddDirectionEventListener = function() {

    if (document.getElementById("add-direction-button") !== null) {
        let i = 0;
        document.getElementById("add-direction-button").addEventListener("click", function (e) {
            e.preventDefault();
            let newDiv = document.createElement("div");
            // and give it some content
            newDiv.innerHTML = `<input class = "form-control" type="text" id="instruction-param-${i}" name="instruction-param" placeholder="Please enter a cooking step.">`
            // add the text node to the newly created div
            document.querySelector("#directions-div").appendChild(newDiv);
            i++;
        })
    }
};
attachAddDirectionEventListener();

