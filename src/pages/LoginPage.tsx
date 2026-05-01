import './LoginPage.css'
import React from 'react'
import { Link } from "react-router-dom"

const RegisterPageConst = () => {
    return(
        <Link to="/RegisterPage">Register Page</Link>
    )
}
RegisterPageConst()

document.querySelector<HTMLDivElement>('#app')!.innerHTML = `
<body>
<img src="images/ver1SWIbckg.jpg" class="backimg" alt="Background image" />
<section class="velkyObdelnik">
<h1>Login Page</h1>

<form name="formularPrihlasovani"  action="form.html" method="post">

  <p><label  class="login" for="item1">username:  </label>
  <input id="item1" class="policko"  name="item1" type="text"></p>
  
  <p><label  class="login" for="item2">password:  </label>
  <input id="item2"  class="policko" name="item2" type="text"></p>
</form>
<a href="/RegisterPage.tsx"><button id="loginButton" class="loginButton" type="button"><a>Login</a></button></a>
{<link to="/RegisterPage">Texttttttttt</link>}

<p>If you aren´t registered yet, please click <a href="/RegisterPage.tsx" class ="registerlink">here</a></p>
    
    
</section>
  
</body>
`

