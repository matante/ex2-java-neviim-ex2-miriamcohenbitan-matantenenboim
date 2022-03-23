function updatePoll(json) {

    console.log(json)
    let question = document.getElementById("pollQuestion");

    question.innerText = json[0]

    let answers = document.getElementById("answers");
    for (let i = 1; i < json.length; i += 2) {
        answers.innerHTML += `<li class="list-group-item">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="optionsRadios">
                                        ${json[i]}
                                        voted: ${json[i+1]}
                                    </label>
                                </div>
                            </li>
        `
    }


}

const f = function () {

    return fetch("/getQuestions", {method: "GET"})
        .then(res => res.json())
        .then(json => {
            updatePoll(json);
        })
        .catch(function (err) {
//todo
        })
}()

