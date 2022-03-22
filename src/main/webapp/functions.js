
function updatePoll(json) {

    let question = document.getElementById("pollQuestion");
    question.innerText = json["title"]
    let answers = document.getElementById("answers");
    for (let i = 1; i < json.values.length(); i++) {
        let answer = (json[`answer${i}`]).toString();
        answers.innerHTML += `<li class="list-group-item">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="optionsRadios">
                                        ${answer}
                                    </label>
                                </div>
                            </li>
        `
    }
    console.log(answers)




}

const f = function() {

    return fetch("/getQuestions", {method: "GET"})
        .then(res => res.json())
        .then(json => {
            updatePoll(json);
            console.log(json)
              })
            .catch(function (err) {
                document.getElementById('loading-image').classList.add('d-none')
                document.querySelector("#data").innerHTML = "NASA servers are not available right now, please try again later";
            })
}()
// (function () {
//

//
//
// })();
//
