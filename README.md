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

## Java-VPN-Client/
│
├── src/
│ ├── main/
│ │ ├── java/vpn/core/
│ │ │ ├── VPNClient.java
│ │ │ ├── VPNGUIApp.java
│ │ │ ├── Authenticator.java
│ │ │ ├── VPNServer.java
│ │ │ ├── ServerLocation.java
│ │ │ ├── screens/
│ │ │ │ ├── WelcomeScreen.java
│ │ │ │ ├── ModeSelectionScreen.java
│ │ │ │ ├── MainInterfaceScreen.java
│ ├── resources/
│ │ ├── ovpn/
│ │ │ ├── us178_tcp80.ovpn
│ │ │ ├── de220_tcp443.ovpn
│ │ │ ├── credentials.txt
│ │ ├── assets/
│ │ │ ├── background-1.jpg
│ │ │ ├── background-2.avif
│ │ │ ├── background-3.jpg
│ │ │ ├── vpn.png


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
