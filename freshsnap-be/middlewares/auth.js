import jwt from "jsonwebtoken";

const Auth = {
  verifyTokenUser(req, res, next) {
    const token = req.cookies.jwt;
    console.log(token);
    if (token) {
      jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, decodedToken) => {
        if (err) {
          console.log(err.message);
          res.status(403).send(`Failed to enter this session, ${err.message} and please re-login`);
        } else {
          req.id = decodedToken.id;
          req.name = decodedToken.name;
          req.email = decodedToken.email;
          return next();
        }
      });
    } else {
      res.status(403).send("Youre not authenticated, please login first");
      console.log("Youre not authenticated");
    }
  },
};

export default Auth;
