import multer from "multer";
import path from "path";
import fs from "fs";
import MulterGoogleCloudStorage from "multer-cloud-storage";
// import uuid from "uuid/v4";
import { Storage } from "@google-cloud/storage";

// CLOUD STORAGE UPLOAD
export const uploadGoogleStorage = multer({
  storage: multer.memoryStorage(),
  fileSize: 5 * 1024 * 1024,
});

const cloudStorage = new Storage({
  projectId: "winter-quanta-351315",
  keyFilename: "google-cloud-key.json",
});

export const bucket = cloudStorage.bucket("freshsnap-image");

const storageMultiple = multer.diskStorage({
  destination: function (req, file, cb) {
    var dir = "public/uploads";
    if (!fs.existsSync(dir)) {
      fs.mkdirSync(dir);
    }
    cb(null, dir);
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + path.extname(file.originalname));
  },
});

const uploadMultiple = multer({
  storage: storageMultiple,
  // limits: { fileSize: 1000000 },
  fileFilter: function (req, file, cb) {
    checkFileType(file, cb);
  },
}).array("image", 12);

// Set storage engine
const storage = multer.diskStorage({
  destination: "public/uploads",
  filename: function (req, file, cb) {
    cb(null, Date.now() + path.extname(file.originalname));
  },
});

export const uploadSingle = multer({
  storage: storage,
  // limits: { fileSize: 1000000 },
  fileFilter: function (req, file, cb) {
    checkFileType(file, cb);
  },
}).single("image");

// // Check file Type
function checkFileType(file, cb) {
  // Allowed ext
  const fileTypes = /jpeg|jpg|png|gif/;
  // Check ext
  const extName = fileTypes.test(path.extname(file.originalname).toLowerCase());
  // Check mime
  const mimeType = fileTypes.test(file.mimetype);

  if (mimeType && extName) {
    return cb(null, true);
  } else {
    cb("Error: Images Only !!!");
  }
}
