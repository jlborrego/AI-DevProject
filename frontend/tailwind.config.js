/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'dark-navy': '#071120',
        'dark-slate': '#101d33',
        'light-text': '#f6f8ff',
        'light-emphasis': '#a8b2d1',
        'primary': '#5b8cff',
        'primary-light': '#91cbff',
      },
      backgroundImage: {
        'gradient-dark': 'linear-gradient(135deg, #071120 0%, #101d33 100%)',
      }
    },
  },
  plugins: [],
}
