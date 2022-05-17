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
