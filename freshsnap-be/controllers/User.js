import Users from "../models/userModel.js";
import { Sequelize } from "sequelize";
import fs from "fs";
import path from "path";
import bcrypt from "bcrypt";

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

export const Register = async (req, res) => {
  const { name, email, password, confirmPassword } = req.body;

  console.log(name, email, password);
  const checkEmail = await Users.findOne({
    where: {
      email: email,
    },
  });

  console.log(checkEmail);

  if (checkEmail) {
    return res.status(400).json({
      msg: "Email has been used!",
    });
  }
  if (!name || !email || !password || !confirmPassword) {
    return res.status(400).json({
      msg: "All fields must be filled!",
    });
  }

  if (password !== confirmPassword) return res.status(400).json({ msg: "Password and confirm password not match!" });
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

  console.log(req.file.filename);
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
    const accessToken = jwt.sign({ userId, name, email }, process.env.ACCESS_TOKEN_SECRET, {
      expiresIn: "1h",
    });
    const refreshToken = jwt.sign({ userId, name, email }, process.env.REFRESH_TOKEN_SECRET, {
      expiresIn: "1d",
    });
    await Users.update(
      { refresh_token: refreshToken },
      {
        where: {
          id: userId,
        },
      }
    );
    res.cookie("refreshToken", refreshToken, {
      httpOnly: true,
      maxAge: 24 * 60 * 60 * 1000,
    });
    res.json({ accessToken });
  } catch (err) {
    res.status(404).json({ msg: "Email not found!" });
  }
};

export const Logout = async (req, res) => {
  const refreshToken = req.cookies.refreshToken;
  if (!refreshToken) return res.sendStatus(204);

  const user = await Users.findAll({
    where: {
      refresh_token: refreshToken,
    },
  });

  if (!user[0]) {
    res.sendStatus(204);
  }

  const userId = user[0].id;

  await Users.update(
    {
      refresh_token: null,
    },
    {
      where: {
        id: userId,
      },
    }
  );

  res.clearCookie("refreshToken");
  return res.sendStatus(200);
};
