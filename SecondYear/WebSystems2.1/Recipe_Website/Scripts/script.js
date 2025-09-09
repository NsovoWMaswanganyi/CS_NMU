const answer = "desserts";
const correctFeedback = "Correct! You may share your recipe with us!";
const wrongFeedback ="Incorrect! Explore the website and try again.";


$(document).ready(function() {

    $("#submitBtn").hide();
    $("#recipe").hide();
    $("#feedback").hide();

});

function checkAnswer() {
    
    const selected = document.querySelector('input[name="recipeType"]:checked');

    if(!selected) {
        alert("please select from the options above before checking!")
        return false;
    }
    
    if(selected.value === answer) {

        const feedback = correctFeedback;
        document.querySelector('#feedback').innerHTML = feedback;
        $("#submitBtn").show("fast");
        $("#recipe").show("fast");
        $("#feedback").show("fast");
        $("#check").hide();

    } else {
        const feedback = wrongFeedback;
        document.querySelector('#feedback').innerHTML = feedback;
        $("#feedback").show("fast");
    }

    return false;
}


function customReset() {
    $("#submitBtn").hide("slow");
    $("#recipe").hide("slow");
    $("#feedback").hide("slow");
    $("#check").show("slow");

    
    $('input[name="recipeType"]').prop('checked', false);
    $('#recipe').val("Type your recipe here");
    $('#firstName').val("");
    $('#email').val("");
    $('#feedback').text("");

    return false; 
}