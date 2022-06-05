import express from "express";
import dotenv from "dotenv";
import cookieParser from "cookie-parser";
import db from "./config/database.js";
import router from "./routes/index.js";
import cors from "cors";
import bodyParser from "body-parser";
import path from "path";
import { fileURLToPath } from "url";

dotenv.config();
const app = express();

app.use(express.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
app.use(express.static(path.join(__dirname, "public")));

try {
  await db.authenticate();
  console.log("Database connected");
} catch (err) {
  console.log(err);
}

app.use(cors());

app.use(express.urlencoded({ extended: true }));

app.get("/", async (req, res) => {
  try {
    res.send(`Welcome to API Page of Freshsnap`);
  } catch (error) {
    console.log(error);
  }
});

app.use(cookieParser());
app.use(express.json());

app.use(router);

app.listen(5000, () => {
  console.log("Server running at port: 5000");
});
