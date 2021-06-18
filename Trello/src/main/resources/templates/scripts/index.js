let status = 0;
function codeAddress()
{
    console.log(status)
    if(status=== 1){
        location.href = 'kanban.html';
    }
    else{
        location.href = 'index.html';
    }
}
$(document).ready(function () {

    $("#task").click(function () {
        if(status === 1){
            location.href = 'kanban.html';
            console.log(status);
        }
    });

    $("#btn1").click(function () {
        $.ajax({
            url: "http://localhost:8080/trello/task",
            type: "GET",
            dataType: "json",
            success: function (responce) {
                console.log(responce);
            },
            error: function (responce) {
                console.log("Что-то пошло не так" + responce);
            }
        });
    });
    $("#logbtn").click(async function () {
        let varData = {
            "username": $("#username").val(),
            "password": $("#password").val(),
        };
        const url = "http://localhost:8080/trello/user/login/" + varData.username;
        let response = await fetch(url, {
            method: 'GET', // *GET, POST, PUT, DELETE, etc.
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })
        const users = await response.json();
        if(varData.username===users["login"] && varData.password===users["passwordHash"]){
            status = 1;
           location.href = 'kanban.html';
        }
        else{
            console.log("Password is incorrect");
            status = 0;
        }
    });

    $("#regbtn").click(async function () {
        let varData = {
            "firstName": $("#firstName").val(),
            "lastName": $("#secondName").val(),
            "middleName": $("#middleName").val(),
            "login": $("#username").val(),
            "passwordHash": $("#password").val(),
            "passwordRepeat": $("#passwordRepeat").val()
        };
        if (varData.passwordRepeat === varData.passwordHash) {
            let response = await fetch("http://localhost:8080/trello/user", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(varData)
            });
            if (response.status === 200) {
                location.href = 'kanban.html';
                status = 1;

            } else if (response.status === 201) {
                location.href = 'kanban.html';
                status = 1;

            } else {
                status = 0;
            }


        } else {
            console.log("Password are incorrect");
            status = 0;
            JSON.stringify(varData)
        }

    });
})