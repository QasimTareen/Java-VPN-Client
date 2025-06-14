# Java VPN Client (Desktop GUI)

A modern, Java-based OpenVPN desktop client built using **JavaFX** with support for multiple servers, authentication, guest mode, dynamic UI, and seamless VPN connectivity.

![VPN Logo](resources/assets/vpn.png)

## 🚀 Features

- 🔒 Secure VPN connectivity using **OpenVPN** and `.ovpn` profiles
- 👤 Dual access: **Sign-in** mode and **Guest** mode
- 🌍 Pre-integrated server support (US, DE, PL, etc.)
- 🎨 Graphical user interface (JavaFX)
- 🔁 Toggle connection (Connect/Disconnect)
- 🔙 Navigation between screens (Welcome, Mode Selection, Main Interface)
- 📂 Embedded resources (Images, Configs, Credentials)
- ✅ Login authentication from local credentials
- 📦 Fully modular structure for easy expansion

---

## 🛠️ Tech Stack

| Component       | Technology              |
|----------------|--------------------------|
| Programming    | Java 17+, Kotlin (planned) |
| UI Framework   | JavaFX                   |
| VPN Backend    | OpenVPN (CLI Integration)|
| Build Tool     | Maven                    |
| OS Support     | Windows, macOS, Linux    |

---

## 📁 Project Structure

The project is organized into clear, modular components:

### 🧠 Core Java Classes (`src/main/java/vpn/core/`)
- `VPNClient.java` – Manages OpenVPN connection and process execution.
- `Authenticator.java` – Validates user login with stored credentials.
- `VPNServer.java` – Represents individual VPN server data.
- `ServerLocation.java` – Holds a list of available server options.
- `VPNGUIApp.java` – Entry point of the GUI application and screen controller.

### 💻 GUI Screens (`src/main/java/vpn/core/screens/`)
- `WelcomeScreen.java` – Initial welcome interface with logo and "Get Started" button.
- `ModeSelectionScreen.java` – Offers Sign-in and Guest Mode options.
- `MainInterfaceScreen.java` – Displays Connect/Disconnect toggle, server list, and back navigation.

### 📦 Resources (`src/main/resources/`)
- `ovpn/`
  - `.ovpn` files (e.g., `us178_tcp80.ovpn`, `de220_tcp443.ovpn`) – VPN configuration profiles.
  - `credentials.txt` – Stores username and password for Sign-in users.
  
- `assets/`
  - `vpn.png` – Logo displayed in welcome screen.
  - `background-1.jpg` – Background for the welcome screen.
  - `background-2.avif` – Background for mode selection screen.
  - `background-3.jpg` – Background for the main VPN interface.



---

## 🔐 Authentication

- Users can log in with credentials stored in `resources/ovpn/credentials.txt`.
- Guest mode skips login and allows limited access (1 server only).
- Sign-in mode enables all 4 configured servers.

---

## 🌐 VPN Servers

Integrated OpenVPN configuration files:

- 🇺🇸 `us178_tcp80.ovpn`
- 🇩🇪 `de220_tcp443.ovpn`
- 🇵🇱 `pl140_udp25000.ovpn`
- 🇨🇦 `ca196_udp53.ovpn`

All configurations are placed inside `resources/ovpn/`.

---

## 📽️ Working Screens (UI Flow)

1. **Welcome Screen** – shows logo + “Get Started” button
2. **Mode Selection Screen** – choose **Sign-In** or **Guest**
3. **Main Interface** – Connect/Disconnect Toggle + Server Dropdown + Back Button

_(Screenshots Placeholder - Add your actual UI screenshots here)_

---

## ▶️ How to Run

### 🖥️ Prerequisites

- Java 17+
- Maven
- OpenVPN installed and added to PATH

### 🔧 Build & Run

```bash
git clone https://github.com/QasimTareen/Java-VPN-Client.git
cd Java-VPN-Client
mvn clean install
java -jar target/Java-VPN-Client.jar

## ⚠️ Ensure OpenVPN is installed and the config files (.ovpn) are correctly mapped in the code.
