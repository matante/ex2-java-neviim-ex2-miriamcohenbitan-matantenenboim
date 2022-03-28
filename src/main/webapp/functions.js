(function () {
    function displayPollAnswers(json) {
        let question = document.getElementById("pollQuestion");
        question.innerText = json[0]

        let answers = document.getElementById("answers");
        for (let i = 1; i < json.length; i++) {
            answers.innerHTML += `<li class="list-group-item">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="optionsRadios" value="${json[i]}">
                                        ${json[i]}  
                                    </label>
                                </div>
                            </li>
        `
        }
    }

    function displayPollVotes(json) {

        let votes = document.getElementById("votes");
        votes.innerText = "";

        for (let i = 0; i < json.length; i++) {
            votes.innerHTML += ` <li class="list-group-item">
                                <div class="radio">
                                    <label>
                                        ${json[i]}
                                    </label>
                                </div>
                            </li>
        `
        }
    }



    function fetchQuestionAnswers() {
        fetch("/api/getPoll", {method: "GET"})
            .then(res => res.json())
            .then(json => {
                displayPollAnswers(json);
            })// add votes
            .catch(function (err) { //todo add catch
            })
    }

    function fetchVotes() {
        fetch("/api/vote", {method: "GET"})
            .then(res => res.json())
            .then(json => {
                displayPollVotes(json);
            })// add votes
            .catch(function (err) { //todo add catch
            })
    }

    document.addEventListener('DOMContentLoaded', () => { // wait for page to load
        fetchQuestionAnswers();
        fetchVotes();


        let form = document.getElementById('inputForm');

        form.addEventListener('submit', submitForm);


    })

    const submitForm = async function (event) {
        event.preventDefault()
        let selectedAnswer = "";
        const answers = document.querySelectorAll('input[name="optionsRadios"]');
        for (const answer of answers) {
            if (answer.checked) {
                selectedAnswer = answer.value;
                break;
            }
        }

        let params = {answer: selectedAnswer}
        await fetch("/api/vote", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'datatype': 'json'
            },
            body: new URLSearchParams(params).toString()
        })
            .then(function (response) {

            }).then(fetchVotes)
            .then(function(json) {
                displayPollVotes(json);
        })
    }


})()


// const f = function () {
//
//     return fetch("/api", {method: "GET"})
//         .then(res => res.json())
//         .then(json => {
//             console.log(json)
//             updatePoll(json);
//         })
//         .catch(function (err) {
// //todo
//         })
// }()

