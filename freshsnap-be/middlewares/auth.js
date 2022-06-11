import jwt from "jsonwebtoken";

const Auth = {
  verifyTokenUser(req, res, next) {
    const authHeader = req.headers["authorization"];
    const token = authHeader && authHeader.split(" ")[1];

    if (token) {
      jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, decodedToken) => {
        if (err) {
          console.log(err.message);
          res.status(403).json({ msg: `Unauthorized, ${err.message} and please re-login!` });
        } else {
          req.id = decodedToken.id;
          req.name = decodedToken.name;
          req.email = decodedToken.email;
          return next();
        }
      });
    } else {
      res.status(403).json({ msg: "You are not authenticated, please login!" });
    }
  },
};

export default Auth;
