import './RegisterPage.css';

document.querySelector<HTMLDivElement>('#app')!.innerHTML = `
    <body>
    <section class="velkyObdelnik">
        <h1>Register Page</h1>

        <form name="formularRegistrace"  action="form.html" method="post">

            <p><label  class="registr" for="item1">Choose your username:  </label>
                <input id="item1" class="policko"  name="item1" type="text"></p>

            <p><label  class="registr" for="item2">Choose a strong password:  </label>
                <input id="item2"  class="policko" name="item2" type="text"></p>

            <p><label  class="registr" for="item2">Please repeat password:  </label>
                <input id="item3"  class="policko" name="item3" type="text"></p>
        </form>
        <a href="LoginPage.tsx"><button id="registerButton" class="registerButton" type="button"><a>Register</a></button></a>

        <p>If you have registered yourself, please log in <a href="LoginPage.tsx" class ="registerlink">here</a></p>


    </section>
    </body>
`
