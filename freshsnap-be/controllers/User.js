import Users from "../models/userModel.js";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

export const getUser = async (req, res) => {
  try {
    const user = await Users.findAll({
      attributes: ["id", "name", "email", "image"],
    });
    res.json(user);
  } catch (error) {
    console.log(error);
  }
};

export const deleteUser = async (req, res) => {
  const id = req.params.id;
  const user = await Users.findAll({
    where: {
      id: id,
    },
  });

  if (!user) {
    return res.status(404).send("User not found!");
  }
  try {
    await Users.destroy({
      where: {
        id: id,
      },
    });

    res.status(200).send("User has been deleted!");
  } catch (error) {
    res.status(500).send(`${error}`);
  }
};

export const getSpecifyUser = async (req, res) => {
  const id = req.params.id;
  try {
    const user = await Users.findOne({
      where: {
        id: id,
      },
    });
    res.json({ user });
  } catch (err) {
    console.log({ message: err.message });
  }
};

export const Register = async (req, res) => {
  const { name, email, password } = req.body;

  const checkEmail = await Users.findOne({
    where: {
      email: email,
    },
  });

  if (checkEmail) {
    return res.status(400).json({
      msg: "Email has been used!",
    });
  }
  if (!name || !email || !password) {
    return res.status(400).json({
      msg: "All fields must be filled!",
    });
  }

  const salt = await bcrypt.genSalt();
  const hashPassword = await bcrypt.hash(password, salt);

  try {
    await Users.create({
      name: name,
      email: email,
      password: hashPassword,
    });
    res.json({ msg: "Registration has been successful!" });
  } catch (err) {
    console.log(err);
  }
};

export const updateUser = async (req, res) => {
  const { name, email, password } = req.body;

  try {
    if (req.file) {
      const upload = "uploads/" + req.file.filename;
      await Users.update(
        { name: name, email: email, password: password, image: upload },
        {
          where: {
            id: req.params.id,
          },
        }
      );
    }
    await Users.update(
      { name: name, email: email, password: password },
      {
        where: {
          id: req.params.id,
        },
      }
    );
    res.json({
      message: "Account has been successfully updated",
    });
  } catch (err) {
    console.log({ message: err.message });
  }
};

export const Login = async (req, res) => {
  try {
    const user = await Users.findAll({
      where: {
        email: req.body.email,
      },
    });

    if (!user) return res.status(400).json({ msg: "The email you entered is not registered!" });

    const match = await bcrypt.compare(req.body.password, user[0].password);
    if (!match) return res.status(400).json({ msg: "The password you entered is incorrect!" });

    const userId = user[0].id;
    const name = user[0].name;
    const email = user[0].email;

    const token = jwt.sign(
      {
        id: userId,
        name: name,
        email: email,
      },
      process.env.ACCESS_TOKEN_SECRET,
      { expiresIn: "1d" }
    );
    res.cookie("jwt", token, {
      httpOnly: true,
      maxAge: 24 * 60 * 60 * 1000,
    });
    res.json({ token });
  } catch (err) {
    console.log(err);
    res.status(404).json({ msg: "Email not found!" });
  }
};

export const Logout = async (req, res) => {
  try {
    const token = null;
    res.cookie("jwt", token);
    return res.status(200).json({ msg: "Logout successfully!" });
  } catch (err) {
    console.log(err.message);
    return res.status(500).json(err);
  }
};
