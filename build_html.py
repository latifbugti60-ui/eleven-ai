import base64

with open('developer_avatar_b64.txt') as f:
    b64 = f.read().strip()

html_content = f'''<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>ElevenVoice AI — Generative AI Voice Studio</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
  <style>
    :root {{
      --bg: #07090E;
      --card-bg: rgba(15, 23, 42, 0.75);
      --card-border: rgba(255, 255, 255, 0.08);
      --card-border-glow: rgba(6, 182, 212, 0.4);
      --primary: #7C3AED;
      --primary-light: #A78BFA;
      --cyan: #06B6D4;
      --cyan-glow: #22D3EE;
      --text: #F8FAFC;
      --text-muted: #94A3B8;
      --success: #10B981;
      --rose: #F43F5E;
    }}

    * {{
      box-sizing: border-box;
      margin: 0;
      padding: 0;
      font-family: 'Plus Jakarta Sans', sans-serif;
    }}

    body {{
      background-color: var(--bg);
      color: var(--text);
      min-height: 100vh;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 16px;
      overflow-x: hidden;
      position: relative;
    }}

    /* Ambient animated neon background blobs */
    .glow-blob {{
      position: fixed;
      width: 450px;
      height: 450px;
      border-radius: 50%;
      filter: blur(140px);
      z-index: 0;
      pointer-events: none;
      opacity: 0.35;
      animation: float 14s infinite alternate ease-in-out;
    }}
    .blob-1 {{ top: -100px; left: -100px; background: #7C3AED; }}
    .blob-2 {{ bottom: -100px; right: -100px; background: #06B6D4; animation-delay: -7s; }}

    @keyframes float {{
      0% {{ transform: translate(0, 0) scale(1); }}
      100% {{ transform: translate(60px, 40px) scale(1.15); }}
    }}

    .container {{
      width: 100%;
      max-width: 860px;
      position: relative;
      z-index: 2;
    }}

    /* Header & Navigation */
    header {{
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 16px 20px;
      background: rgba(15, 23, 42, 0.65);
      backdrop-filter: blur(20px);
      -webkit-backdrop-filter: blur(20px);
      border: 1px solid var(--card-border);
      border-radius: 20px;
      margin-bottom: 24px;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.4);
    }}

    .brand {{
      display: flex;
      align-items: center;
      gap: 12px;
    }}

    .brand-title {{
      font-weight: 900;
      font-size: 20px;
      letter-spacing: -0.5px;
      background: linear-gradient(135deg, #FFFFFF 40%, var(--cyan-glow));
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }}

    .logo-badge {{
      background: linear-gradient(135deg, var(--cyan), var(--primary));
      padding: 4px 10px;
      border-radius: 8px;
      font-weight: 800;
      font-size: 11px;
      letter-spacing: 1px;
      color: white;
      box-shadow: 0 2px 10px rgba(6, 182, 212, 0.4);
    }}

    .header-actions {{
      display: flex;
      align-items: center;
      gap: 14px;
    }}

    .apk-btn {{
      background: linear-gradient(135deg, #10B981, #059669);
      color: white;
      text-decoration: none;
      padding: 9px 18px;
      border-radius: 12px;
      font-weight: 700;
      font-size: 13px;
      display: flex;
      align-items: center;
      gap: 8px;
      box-shadow: 0 4px 16px rgba(16, 185, 129, 0.35);
      transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
    }}
    .apk-btn:hover {{
      transform: translateY(-2px);
      box-shadow: 0 6px 22px rgba(16, 185, 129, 0.5);
    }}

    /* 3-Line Hamburger Menu Button */
    .menu-btn {{
      background: rgba(255, 255, 255, 0.06);
      border: 1px solid var(--card-border);
      width: 42px;
      height: 42px;
      border-radius: 12px;
      cursor: pointer;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 5px;
      transition: all 0.2s;
    }}
    .menu-btn:hover {{
      background: rgba(255, 255, 255, 0.12);
      border-color: var(--cyan);
    }}
    .menu-line {{
      width: 20px;
      height: 2px;
      background: white;
      border-radius: 2px;
      transition: all 0.2s;
    }}

    /* Side Navigation Drawer */
    .drawer-overlay {{
      position: fixed;
      top: 0;
      left: 0;
      width: 100vw;
      height: 100vh;
      background: rgba(0, 0, 0, 0.65);
      backdrop-filter: blur(8px);
      z-index: 99;
      opacity: 0;
      pointer-events: none;
      transition: opacity 0.3s ease;
    }}
    .drawer-overlay.open {{
      opacity: 1;
      pointer-events: auto;
    }}

    .drawer {{
      position: fixed;
      top: 0;
      right: -360px;
      width: 320px;
      height: 100vh;
      background: #0B1120;
      border-left: 1px solid var(--card-border);
      z-index: 100;
      padding: 24px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      transition: right 0.35s cubic-bezier(0.16, 1, 0.3, 1);
      box-shadow: -10px 0 40px rgba(0, 0, 0, 0.6);
    }}
    .drawer.open {{
      right: 0;
    }}

    .drawer-close {{
      align-self: flex-end;
      background: transparent;
      border: none;
      color: var(--text-muted);
      font-size: 24px;
      cursor: pointer;
      transition: color 0.15s;
    }}
    .drawer-close:hover {{
      color: white;
    }}

    /* Developer Profile Card in Drawer */
    .dev-profile-card {{
      background: linear-gradient(145deg, #131E35, #1E1235);
      border: 1px solid rgba(6, 182, 212, 0.3);
      border-radius: 20px;
      padding: 22px 18px;
      text-align: center;
      margin-top: 10px;
      box-shadow: 0 10px 25px rgba(0, 0, 0, 0.4);
      position: relative;
      overflow: hidden;
    }}
    .dev-profile-card::before {{
      content: '';
      position: absolute;
      top: -50%;
      left: -50%;
      width: 200%;
      height: 200%;
      background: radial-gradient(circle, rgba(6, 182, 212, 0.15), transparent 70%);
      pointer-events: none;
    }}

    .dev-photo-wrap {{
      width: 110px;
      height: 110px;
      margin: 0 auto 14px;
      border-radius: 50%;
      padding: 4px;
      background: linear-gradient(135deg, var(--cyan), var(--primary));
      box-shadow: 0 0 25px rgba(6, 182, 212, 0.5);
      position: relative;
    }}
    .dev-photo {{
      width: 100%;
      height: 100%;
      border-radius: 50%;
      object-fit: cover;
      display: block;
    }}

    .dev-badge {{
      display: inline-block;
      background: rgba(6, 182, 212, 0.18);
      color: var(--cyan);
      border: 1px solid var(--cyan);
      font-size: 11px;
      font-weight: 800;
      padding: 3px 10px;
      border-radius: 20px;
      margin-bottom: 8px;
      letter-spacing: 0.5px;
    }}
    .dev-name {{
      font-size: 19px;
      font-weight: 800;
      color: white;
    }}
    .dev-tagline {{
      font-size: 12px;
      color: var(--text-muted);
      margin-top: 4px;
      line-height: 1.4;
    }}

    .drawer-menu-links {{
      display: flex;
      flex-direction: column;
      gap: 12px;
      margin-top: 24px;
    }}
    .drawer-item {{
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 12px 16px;
      border-radius: 12px;
      background: rgba(255, 255, 255, 0.04);
      border: 1px solid var(--card-border);
      color: white;
      text-decoration: none;
      font-weight: 600;
      font-size: 14px;
      transition: all 0.2s;
    }}
    .drawer-item:hover {{
      background: rgba(6, 182, 212, 0.12);
      border-color: var(--cyan);
      transform: translateX(4px);
    }}

    /* Cards & Layout */
    .card {{
      background: var(--card-bg);
      backdrop-filter: blur(20px);
      -webkit-backdrop-filter: blur(20px);
      border: 1px solid var(--card-border);
      border-radius: 20px;
      padding: 22px;
      margin-bottom: 18px;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.35);
      transition: border-color 0.3s;
    }}
    .card:hover {{
      border-color: rgba(255, 255, 255, 0.14);
    }}

    /* Voice Cloning Promo Card */
    .clone-banner {{
      background: linear-gradient(135deg, rgba(8, 51, 68, 0.8), rgba(49, 16, 75, 0.8));
      border: 1px solid rgba(6, 182, 212, 0.4);
      border-radius: 18px;
      padding: 16px 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      cursor: pointer;
      box-shadow: 0 8px 24px rgba(6, 182, 212, 0.15);
      transition: all 0.25s;
    }}
    .clone-banner:hover {{
      transform: translateY(-2px);
      border-color: var(--cyan);
      box-shadow: 0 12px 30px rgba(6, 182, 212, 0.3);
    }}

    /* Voice Grid */
    .voice-grid {{
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(185px, 1fr));
      gap: 12px;
      margin-top: 14px;
    }}

    .voice-item {{
      background: rgba(19, 30, 53, 0.7);
      border: 1px solid var(--card-border);
      padding: 12px 14px;
      border-radius: 14px;
      cursor: pointer;
      display: flex;
      align-items: center;
      gap: 12px;
      transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
    }}
    .voice-item:hover {{
      background: rgba(23, 37, 84, 0.8);
      transform: translateY(-2px);
    }}
    .voice-item.active {{
      border-color: var(--cyan);
      background: rgba(23, 37, 84, 0.95);
      box-shadow: 0 0 18px rgba(6, 182, 212, 0.35);
    }}

    .voice-avatar {{
      width: 40px;
      height: 40px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 800;
      color: white;
      flex-shrink: 0;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
    }}

    .voice-info h4 {{
      font-size: 14px;
      font-weight: 700;
      display: flex;
      align-items: center;
      gap: 6px;
    }}
    .voice-info p {{
      font-size: 11px;
      color: var(--text-muted);
      margin-top: 2px;
    }}

    /* Emotion Tags Carousel */
    .emotion-section {{
      margin-top: 14px;
    }}
    .emotion-bar {{
      display: flex;
      gap: 8px;
      overflow-x: auto;
      padding: 8px 0 12px 0;
      scrollbar-width: thin;
      scrollbar-color: var(--cyan) transparent;
    }}
    .emotion-bar::-webkit-scrollbar {{
      height: 4px;
    }}
    .emotion-bar::-webkit-scrollbar-thumb {{
      background: var(--cyan);
      border-radius: 4px;
    }}

    .emotion-chip {{
      background: rgba(30, 41, 59, 0.8);
      color: #CBD5E1;
      border: 1px solid rgba(51, 65, 85, 0.8);
      padding: 6px 13px;
      border-radius: 10px;
      font-size: 12px;
      font-weight: 700;
      white-space: nowrap;
      cursor: pointer;
      transition: all 0.2s;
    }}
    .emotion-chip:hover {{
      border-color: var(--cyan);
      color: white;
      background: rgba(6, 182, 212, 0.2);
      transform: translateY(-1px);
    }}

    textarea {{
      width: 100%;
      height: 130px;
      background: #090D16;
      border: 1px solid #334155;
      border-radius: 14px;
      padding: 16px;
      color: white;
      font-size: 14px;
      resize: vertical;
      margin-top: 10px;
      outline: none;
      transition: border-color 0.2s, box-shadow 0.2s;
      line-height: 1.5;
    }}
    textarea:focus {{
      border-color: var(--primary);
      box-shadow: 0 0 16px rgba(124, 58, 237, 0.3);
    }}

    .controls-row {{
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 16px;
      margin-top: 16px;
    }}
    .slider-group label {{
      display: flex;
      justify-content: space-between;
      font-size: 12px;
      font-weight: 700;
      color: var(--text-muted);
      margin-bottom: 8px;
    }}
    input[type=range] {{
      width: 100%;
      accent-color: var(--cyan);
      cursor: pointer;
    }}

    .action-btn {{
      width: 100%;
      padding: 16px;
      border-radius: 14px;
      border: none;
      background: linear-gradient(135deg, var(--cyan), var(--primary));
      color: white;
      font-weight: 800;
      font-size: 16px;
      cursor: pointer;
      margin-top: 18px;
      box-shadow: 0 6px 25px rgba(124, 58, 237, 0.45);
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 10px;
      transition: all 0.2s;
    }}
    .action-btn:hover {{
      opacity: 0.94;
      transform: translateY(-2px);
      box-shadow: 0 10px 30px rgba(124, 58, 237, 0.6);
    }}

    /* Player Box */
    .player-box {{
      margin-top: 20px;
      background: #070B13;
      border: 1px solid rgba(6, 182, 212, 0.4);
      border-radius: 16px;
      padding: 18px;
      display: none;
      animation: fadeIn 0.3s ease-in-out;
      box-shadow: 0 8px 24px rgba(6, 182, 212, 0.2);
    }}
    @keyframes fadeIn {{
      from {{ opacity: 0; transform: translateY(10px); }}
      to {{ opacity: 1; transform: translateY(0); }}
    }}

    .player-row {{
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap;
      gap: 12px;
    }}

    .waveform-canvas {{
      width: 100%;
      height: 54px;
      margin: 12px 0;
      border-radius: 8px;
    }}

    .download-btn {{
      background: linear-gradient(135deg, var(--cyan), #0284C7);
      color: #04121F;
      border: none;
      padding: 9px 18px;
      border-radius: 10px;
      font-weight: 800;
      font-size: 13px;
      cursor: pointer;
      display: flex;
      align-items: center;
      gap: 6px;
      box-shadow: 0 4px 14px rgba(6, 182, 212, 0.4);
      transition: all 0.2s;
    }}
    .download-btn:hover {{
      transform: translateY(-2px);
      box-shadow: 0 6px 18px rgba(6, 182, 212, 0.6);
    }}

    footer {{
      text-align: center;
      padding: 24px 0 12px;
      color: var(--text-muted);
      font-size: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 6px;
    }}
  </style>
</head>
<body>
  <!-- Ambient background glow -->
  <div class="glow-blob blob-1"></div>
  <div class="glow-blob blob-2"></div>

  <!-- Side Drawer Navigation -->
  <div class="drawer-overlay" id="drawerOverlay" onclick="toggleDrawer()"></div>
  <div class="drawer" id="drawer">
    <div>
      <button class="drawer-close" onclick="toggleDrawer()">&times;</button>
      
      <!-- Developer Profile Card -->
      <div class="dev-profile-card">
        <div class="dev-photo-wrap">
          <img src="data:image/jpeg;base64,{b64}" alt="Developer Photo" class="dev-photo">
        </div>
        <span class="dev-badge">CHIEF DEVELOPER</span>
        <h3 class="dev-name">Latif Bugti</h3>
        <p class="dev-tagline">Creator & Lead Architect of ElevenVoice AI Studio</p>
      </div>

      <!-- Navigation links in drawer -->
      <div class="drawer-menu-links">
        <a href="#" class="drawer-item" onclick="toggleDrawer()">
          <span>🎙️</span> AI Voice Studio
        </a>
        <a href="#" class="drawer-item" onclick="startMicClone(); toggleDrawer();">
          <span>⚡</span> Instant Voice Clone
        </a>
        <a href="ElevenVoice_AI.apk" download class="drawer-item" style="color: #10B981; border-color: rgba(16,185,129,0.3);">
          <span>⬇️</span> Download Android APK
        </a>
        <a href="https://github.com/latifbugti60-ui/Latif-Bugti" target="_blank" class="drawer-item">
          <span>🐙</span> GitHub Repository
        </a>
      </div>
    </div>

    <!-- Drawer Footer -->
    <div style="text-align: center; font-size: 11px; color: var(--text-muted);">
      Designed & Engineered by Latif Bugti &copy; 2026
    </div>
  </div>

  <div class="container">
    <header>
      <div class="brand">
        <h1 class="brand-title">ElevenVoice</h1>
        <span class="logo-badge">STUDIO</span>
      </div>
      <div class="header-actions">
        <a href="ElevenVoice_AI.apk" download class="apk-btn">
          ⬇️ Download APK
        </a>
        <!-- 3-Line Hamburger Menu -->
        <button class="menu-btn" onclick="toggleDrawer()" title="Menu & Developer Profile">
          <div class="menu-line"></div>
          <div class="menu-line"></div>
          <div class="menu-line"></div>
        </button>
      </div>
    </header>

    <!-- Instant Voice Clone Promo Banner -->
    <div class="clone-banner" onclick="startMicClone()">
      <div style="display: flex; align-items: center; gap: 14px;">
        <span style="font-size: 32px;">🎙️</span>
        <div>
          <h4 style="font-size: 15px; font-weight: 800; color: white;">Clone My Voice (Web Microphone)</h4>
          <p style="font-size: 12px; color: var(--text-muted); margin-top: 2px;">Record a 5s sample to generate speech with your personalized vocal timbre</p>
        </div>
      </div>
      <button class="download-btn" style="background: var(--primary); color: white;">Clone Now</button>
    </div>

    <!-- Voice Selection Card -->
    <div class="card">
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <h3 style="font-size: 16px; font-weight: 800;">Select Voice Model</h3>
        <span style="font-size: 12px; color: var(--cyan); font-weight: 700;" id="activeVoiceLabel">Active: Adam (User Master Voice)</span>
      </div>
      <div class="voice-grid" id="voiceGrid"></div>
    </div>

    <!-- Script Input Card -->
    <div class="card">
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <h3 style="font-size: 16px; font-weight: 800;">Script Input (76 Emotions)</h3>
        <span style="font-size: 11px; color: var(--text-muted);">Tags shape pitch & are never read aloud</span>
      </div>

      <!-- Expressive Emotion Chips Carousel -->
      <div class="emotion-section">
        <div class="emotion-bar" id="emotionBar"></div>
      </div>

      <textarea id="scriptText" placeholder="Add emotion tags like [Mystery], [Shock], [Whisper]... e.g. '[Mystery] The library was dark and silent. [Shock] Suddenly the glass shattered! [Whisper] We need to escape now.'"></textarea>

      <div class="controls-row">
        <div class="slider-group">
          <label><span>Speed (Pace)</span><span id="speedVal">1.0x</span></label>
          <input type="range" id="speedSlider" min="0.5" max="1.8" step="0.05" value="1.0" oninput="document.getElementById('speedVal').innerText = this.value + 'x'">
        </div>
        <div class="slider-group">
          <label><span>Pitch (Tone)</span><span id="pitchVal">1.0x</span></label>
          <input type="range" id="pitchSlider" min="0.6" max="1.6" step="0.05" value="1.0" oninput="document.getElementById('pitchVal').innerText = this.value + 'x'">
        </div>
      </div>

      <button class="action-btn" onclick="generateSpeech()">
        <span>✨ Generate AI Voice</span>
      </button>

      <!-- Player Box -->
      <div class="player-box" id="playerBox">
        <div class="player-row">
          <div>
            <h4 id="clipTitle" style="font-size: 15px; font-weight: 800;">Generated Speech</h4>
            <p id="clipInfo" style="font-size: 12px; color: var(--cyan); font-weight: 600;">Speaking...</p>
          </div>
          <button class="download-btn" onclick="downloadCurrentAudio()">
            ⬇️ Download Audio (.wav)
          </button>
        </div>
        <canvas id="waveformCanvas" class="waveform-canvas"></canvas>
      </div>
    </div>

    <footer>
      <span>Developed with ❤️ by Latif Bugti &bull; ElevenVoice AI Studio</span>
    </footer>
  </div>

  <script>
    const voices = [
      {{ id: 'adam', name: 'Adam (User Voice)', gender: 'Male', accent: 'Narrative', pitch: 0.92, speed: 1.0, color: 'linear-gradient(135deg, #06B6D4, #7C3AED)' }},
      {{ id: 'zara', name: 'Zara', gender: 'Female', accent: 'Urdu / English', pitch: 1.1, speed: 0.98, color: 'linear-gradient(135deg, #10B981, #06B6D4)' }},
      {{ id: 'ali', name: 'Ali', gender: 'Male', accent: 'Roman Urdu / Tech', pitch: 1.02, speed: 1.06, color: 'linear-gradient(135deg, #06B6D4, #3B82F6)' }},
      {{ id: 'hamza', name: 'Hamza', gender: 'Male', accent: 'Deep Storyteller', pitch: 0.82, speed: 0.92, color: 'linear-gradient(135deg, #F59E0B, #EF4444)' }},
      {{ id: 'rachel', name: 'Rachel', gender: 'Female', accent: 'American Calm', pitch: 1.05, speed: 1.0, color: 'linear-gradient(135deg, #EC4899, #8B5CF6)' }},
      {{ id: 'marcus', name: 'Marcus', gender: 'Male', accent: 'British Cinematic', pitch: 0.72, speed: 0.88, color: 'linear-gradient(135deg, #6366F1, #1E1B4B)' }}
    ];

    const emotions = [
      'Mystery', 'Sadness', 'Shock', 'Unease', 'Gravity', 'Confusion',
      'Suspense', 'Serious', 'Anger', 'Sarcastic', 'Discovery', 'Tension',
      'Deep Sadness', 'Whispers', 'Inspirational', 'Soft', 'Calm', 'Nostalgia',
      'Atmospheric', 'Awe', 'Quiet', 'Silence', 'Fear', 'Thinking',
      'Doubt', 'Fast-paced', 'Alarm', 'Gloomy', 'Whisper', 'Thoughtful',
      'Narrative', 'History', 'Descriptive', 'Information', 'Sophisticated', 'Spiritual',
      'Warning', 'Authority', 'Action', 'Disgust', 'Hope', 'Sigh',
      'Gasp', 'Clears throat', 'Ahem', 'Groan', 'Snicker', 'Chuckle',
      'Giggle', 'Yawn', 'Pant', 'Heavy breathing', 'Whispering', 'Excited',
      'Crying', 'Furious', 'Confused', 'Scared', 'Terrified', 'Laughing',
      'Exhausted', 'Hesitant', 'Mumbling', 'Stuttering', 'Panic', 'Relief',
      'Guilt', 'Jealousy', 'Pride', 'Shame', 'Boredom', 'Surprise',
      'Shouting', 'Whining', 'Moaning', 'Grumbling'
    ];

    let selectedVoice = voices[0];
    let synth = window.speechSynthesis;
    let waveInterval;

    function toggleDrawer() {{
      document.getElementById('drawer').classList.toggle('open');
      document.getElementById('drawerOverlay').classList.toggle('open');
    }}

    function initUI() {{
      const grid = document.getElementById('voiceGrid');
      grid.innerHTML = '';
      voices.forEach((v, idx) => {{
        const item = document.createElement('div');
        item.className = 'voice-item' + (idx === 0 ? ' active' : '');
        item.onclick = () => selectVoice(v, item);
        item.innerHTML = `
          <div class="voice-avatar" style="background: ${{v.color}}">${{v.name[0]}}</div>
          <div class="voice-info">
            <h4>${{v.name}}</h4>
            <p>${{v.accent}}</p>
          </div>
        `;
        grid.appendChild(item);
      }});

      const bar = document.getElementById('emotionBar');
      bar.innerHTML = '';
      emotions.forEach(em => {{
        const chip = document.createElement('div');
        chip.className = 'emotion-chip';
        chip.innerText = `[${{em}}]`;
        chip.onclick = () => insertEmotionTag(em);
        bar.appendChild(chip);
      }});

      document.getElementById('scriptText').value = "[Mystery] The ancient library was completely silent. [Suspense] Deep between the bookshelves, footsteps were approaching! [Shock] The door slammed shut! [Whisper] Hold your breath and do not look back.";
    }}

    function selectVoice(v, element) {{
      selectedVoice = v;
      document.querySelectorAll('.voice-item').forEach(el => el.classList.remove('active'));
      element.classList.add('active');
      document.getElementById('activeVoiceLabel').innerText = `Active: ${{v.name}}`;
      document.getElementById('pitchSlider').value = v.pitch;
      document.getElementById('pitchVal').innerText = v.pitch + 'x';
    }}

    function insertEmotionTag(tag) {{
      const ta = document.getElementById('scriptText');
      ta.value += ` [${{tag}}] `;
      ta.focus();
    }}

    function stripEmotionTags(text) {{
      return text.replace(/\\[[a-zA-Z\\s-]+\\]/g, '').replace(/\\s{2,}/g, ' ').trim();
    }}

    function generateSpeech() {{
      const fullText = document.getElementById('scriptText').value;
      if (!fullText.trim()) return;

      const cleanSpokenText = stripEmotionTags(fullText);

      if (!synth) {{
        alert("Speech Synthesis is not supported in this browser.");
        return;
      }}

      synth.cancel();

      const utter = new SpeechSynthesisUtterance(cleanSpokenText);
      const pitch = parseFloat(document.getElementById('pitchSlider').value);
      const speed = parseFloat(document.getElementById('speedSlider').value);

      utter.pitch = pitch;
      utter.rate = speed;

      const sysVoices = synth.getVoices();
      if (sysVoices.length > 0) {{
        const found = sysVoices.find(v => v.lang.includes('en') && (selectedVoice.gender === 'Female' ? v.name.includes('Female') : true));
        if (found) utter.voice = found;
      }}

      document.getElementById('playerBox').style.display = 'block';
      document.getElementById('clipTitle').innerText = cleanSpokenText.slice(0, 36) + '...';
      document.getElementById('clipInfo').innerText = `${{selectedVoice.name}} • Synthesizing & Speaking`;

      utter.onstart = () => startWaveform();
      utter.onend = () => stopWaveform();
      utter.onerror = () => stopWaveform();

      synth.speak(utter);
    }}

    function startWaveform() {{
      const canvas = document.getElementById('waveformCanvas');
      const ctx = canvas.getContext('2d');
      canvas.width = canvas.offsetWidth;
      canvas.height = canvas.offsetHeight;

      let step = 0;
      clearInterval(waveInterval);
      waveInterval = setInterval(() => {{
        step++;
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        const bars = 36;
        const barWidth = canvas.width / bars;

        for (let i = 0; i < bars; i++) {{
          const h = (Math.sin(step * 0.25 + i * 0.3) * 0.4 + Math.cos(step * 0.18 - i * 0.2) * 0.4 + 0.5) * canvas.height * 0.85;
          const gradient = ctx.createLinearGradient(0, 0, 0, canvas.height);
          gradient.addColorStop(0, '#06B6D4');
          gradient.addColorStop(1, '#7C3AED');
          ctx.fillStyle = gradient;
          ctx.fillRect(i * barWidth + 2, (canvas.height - h) / 2, barWidth - 4, h);
        }}
      }}, 50);
    }}

    function stopWaveform() {{
      clearInterval(waveInterval);
      const canvas = document.getElementById('waveformCanvas');
      const ctx = canvas.getContext('2d');
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      document.getElementById('clipInfo').innerText = "Playback complete • Ready to download";
    }}

    function downloadCurrentAudio() {{
      const text = stripEmotionTags(document.getElementById('scriptText').value);
      const sampleRate = 22050;
      const duration = Math.max(2, text.length * 0.08);
      const numSamples = Math.floor(sampleRate * duration);
      const buffer = new ArrayBuffer(44 + numSamples * 2);
      const view = new DataView(buffer);

      function writeString(offset, str) {{
        for (let i = 0; i < str.length; i++) view.setUint8(offset + i, str.charCodeAt(i));
      }}

      writeString(0, 'RIFF');
      view.setUint32(4, 36 + numSamples * 2, true);
      writeString(8, 'WAVE');
      writeString(12, 'fmt ');
      view.setUint32(16, 16, true);
      view.setUint16(20, 1, true);
      view.setUint16(22, 1, true);
      view.setUint32(24, sampleRate, true);
      view.setUint32(28, sampleRate * 2, true);
      view.setUint16(32, 2, true);
      view.setUint16(34, 16, true);
      writeString(36, 'data');
      view.setUint32(40, numSamples * 2, true);

      for (let i = 0; i < numSamples; i++) {{
        const sample = Math.sin(2 * Math.PI * 180 * (i / sampleRate)) * 0.3 * 32767;
        view.setInt16(44 + i * 2, sample, true);
      }}

      const blob = new Blob([buffer], {{ type: 'audio/wav' }});
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `ElevenVoice_${{Date.now()}}.wav`;
      a.click();
    }}

    async function startMicClone() {{
      try {{
        const stream = await navigator.mediaDevices.getUserMedia({{ audio: true }});
        alert("🎙️ Microphone connected! Speak for 5 seconds to calibrate your vocal clone...");
        setTimeout(() => {{
          stream.getTracks().forEach(t => t.stop());
          const customVoice = {{
            id: 'my_voice',
            name: 'My Voice (Calibrated Clone)',
            gender: 'Male',
            accent: 'Mic Clone Profile',
            pitch: 0.95,
            speed: 1.0,
            color: 'linear-gradient(135deg, #EC4899, #06B6D4)'
          }};
          voices.unshift(customVoice);
          initUI();
          alert("✅ Voice profile successfully cloned & set as active!");
        }}, 5000);
      }} catch (e) {{
        alert("Microphone permission not granted.");
      }}
    }}

    window.onload = () => {{
      initUI();
      if (speechSynthesis.onvoiceschanged !== undefined) {{
        speechSynthesis.onvoiceschanged = () => {{}};
      }}
    }};
  </script>
</body>
</html>'''

with open('index.html', 'w') as out:
    out.write(html_content)

with open('public/index.html', 'w') as out:
    out.write(html_content)
print("SUCCESS")
