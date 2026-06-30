import { Box, CircularProgress, Typography } from "@mui/material";

const PageLoader = () => {
  return (
    <Box
      sx={{
        position: "fixed",
        inset: 0,
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        bgcolor: "rgba(255,255,255,0.9)",
        zIndex: 9999,
      }}
    >
      <CircularProgress size={50} />

      <Typography
        variant="body1"
        sx={{
          mt: 2,
        }}
      >
        Loading...
      </Typography>
    </Box>
  );
};

export default PageLoader;