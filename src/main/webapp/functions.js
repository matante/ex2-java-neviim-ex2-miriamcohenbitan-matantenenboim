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
        console.log(json)
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
        let votingError = document.getElementById('invalidVote');
        event.preventDefault()
        let selectedAnswer = "";
        const answers = document.querySelectorAll('input[name="optionsRadios"]');
        for (const answer of answers) {
            if (answer.checked) {
                selectedAnswer = answer.value;
                break;
            }
        }
        await fetch("/api/vote", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'datatype': 'json'
            },
            body: new URLSearchParams({answer: selectedAnswer}).toString()
        }).then((res) => {
            console.log("status: " + res.status)
            switch (res.status) {
                case 200:
                    votingError.classList.add('d-none');
                    break;
                case 400:
                    votingError.classList.remove('d-none');
                    break;
            }
        })
            .then(fetchVotes)
            .catch(function (err){
                console.log("in error!!!!" + err)
            })
        // todo need to see why console prints 400 as error


        // await fetch("/api/vote", {
        //     method: 'POST',
        //     headers: {
        //         'Content-Type': 'application/x-www-form-urlencoded',
        //         'datatype': 'json'
        //     },
        //     body: new URLSearchParams({answer: selectedAnswer}).toString()
        // })
        //     .then(function (response) {
        //     })
        //     .then(fetchVotes)
        //     .then(function (response) {
        //     })
        //     .then(function (json) {
        //         console.log("new json")
        //         console.log(json)
        //         displayPollVotes(json);
        //     }).catch()//todo
    }


})()


