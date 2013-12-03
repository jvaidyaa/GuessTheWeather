<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Guess The Weather</title>
<link href="css/normalize.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
>
<script src="js/jquery.js"></script>
<script src="js/test.js"></script>
<script src="js/ajaxProvider.js"></script>


<!--[if lt IE 9]>
	<link rel="stylesheet" type="text/css" href="assets/css/ie8-and-down.css" />
    <script src="js/html5shiv-minified.js"></script>
<![endif]-->
</head>
<body>
<div class="wrapper">
  <header id="header">
    <div class="container">
      <div class="logo"> <a href="#"><img src="images/logo.png" alt="Guess The Weather"></a> </div>
    </div>
  </header>
  <div class="content-border"></div>
  <section class="content">
    <div class="container clearfix">
      <article class="page-content">
        <h3 class="big-heading">The Wheel of Weather</h3>
        <section class="page-inner">
          <div class="feature-img"> <img src="images/temprature.png" alt=""> </div>
          <h3>Welcome</h3>
          <form action="" method="post">
            <p>
              <label>Date of Prediction</label>
              <input type="text" name="dop" id="dop" size="7">
            </p>
            <br>
            <br>
            <p>
              <label>Zip Code</label>
              <input type="text" name="zip" id="zip" size="7">
            </p>
            <br>
            <br>
            <p>
              <label>Email ID</label>
              <input type="text" name="email" id="email"  size="15">
            </p>
            <br>
            <br>
            <p>
              <label>Please enter your Predction</label>
              <input type="text" name="predction" id="prediction" size="7">
            </p>
            <p>
              <button type="button" onclick="save()">save</button> 
            </p>
          </form>
        </section>
      </article>
      <aside>
        <h3>Wall of Fame</h3>
        <p>Oracle of the Week</p>
      </aside>
      <div class="clear"></div>
      <section class="page-bottom">
        <p>Rules & Regulation</p>
      </section>
    </div>
  </section>
  <footer> </footer>
</div>
</body>
</html>
