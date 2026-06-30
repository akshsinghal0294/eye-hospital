import { createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    primary: {
      main: "#1976d2",
    },
    secondary: {
      main: "#dc004e",
    },
  },

  shape: {
    borderRadius: 8,
  },

  typography: {
    fontFamily: "Roboto, Arial, sans-serif",

    h1: {
      fontWeight: 600,
    },
    h2: {
      fontWeight: 600,
    },
    h3: {
      fontWeight: 600,
    },
    h4: {
      fontWeight: 600,
    },
    h5: {
      fontWeight: 600,
    },
    h6: {
      fontWeight: 600,
    },

    button: {
      textTransform: "none",
      fontWeight: 500,
    },
  },

  components: {
    MuiCard: {
      defaultProps: {
        elevation: 2,
      },
    },

    MuiTextField: {
      defaultProps: {
        size: "medium",
        variant: "outlined",
      },
    },

    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: "none",
          borderRadius: 8,
        },
      },
    },

    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          borderRadius: 8,
        },
      },
    },
  },
});

export default theme;