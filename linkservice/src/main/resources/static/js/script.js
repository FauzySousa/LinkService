document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector(".shortener-form");
  const inputUrl = document.getElementById("urlOriginal");

  if (form) {
    form.addEventListener("submit", async (event) => {
      event.preventDefault();

      const originalUrl = inputUrl.value.trim();
      if (!originalUrl) return;

      try {
        
        const response = await fetch("/api/v1/links/encurtar", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ urlOriginal: originalUrl }),
        });

        if (!response.ok) {
          throw new Error("Erro ao processar o link no servidor.");
        }

        const data = await response.json();

        // Exibe o painel de sucesso contendo a url gerada
        renderizarSucesso(data.urlEncurtada);
      } catch (error) {
        console.error("Erro na requisição:", error);
        renderizarErro();
      }
    });
  }

  function renderizarSucesso(urlGerada) {
    const successCard = document.querySelector(".success-card");
    const errorCard = document.querySelector(".error-card");
    const resultLink = document.querySelector(".result-link");

    if (errorCard) errorCard.style.display = "none";

    if (successCard && resultLink) {
      resultLink.textContent = urlGerada;
      resultLink.href = urlGerada;
      successCard.style.display = "grid";
    }
  }

  function renderizarErro() {
    const successCard = document.querySelector(".success-card");
    const errorCard = document.querySelector(".error-card");

    if (successCard) successCard.style.display = "none";
    if (errorCard) errorCard.style.display = "grid";
  }

  const copyButton = document.querySelector("[data-copy-url]");

  if (copyButton) {
    const buttonLabel = copyButton.querySelector(".copy-button-label");
    const buttonIcon = copyButton.querySelector(".copy-button-icon");

    const originalLabel = buttonLabel ? buttonLabel.textContent : "Copiar";
    const originalIcon = buttonIcon ? buttonIcon.innerHTML : "";

    const successIcon = `
            <path d="M9.55 18.2 3.85 12.5l1.4-1.4 4.3 4.3 9.2-9.2 1.4 1.4z"></path>
        `;

    let resetTimer;

    const setCopiedState = () => {
      copyButton.classList.add("is-copied");

      if (buttonLabel) buttonLabel.textContent = "Copiado!";
      if (buttonIcon) buttonIcon.innerHTML = successIcon;

      window.clearTimeout(resetTimer);
      resetTimer = window.setTimeout(() => {
        copyButton.classList.remove("is-copied");

        if (buttonLabel) buttonLabel.textContent = originalLabel;
        if (buttonIcon) buttonIcon.innerHTML = originalIcon;
      }, 2000);
    };

    const copyToClipboard = async (text) => {
      if (navigator.clipboard && window.isSecureContext) {
        await navigator.clipboard.writeText(text);
        return;
      }

      const tempInput = document.createElement("textarea");
      tempInput.value = text;
      tempInput.setAttribute("readonly", "");
      tempInput.style.position = "absolute";
      tempInput.style.left = "-9999px";

      document.body.appendChild(tempInput);
      tempInput.select();
      document.execCommand("copy");
      document.body.removeChild(tempInput);
    };

    copyButton.addEventListener("click", async () => {
      const resultLink = document.querySelector(".result-link");
      const urlText = resultLink ? resultLink.textContent.trim() : "";

      if (!urlText) return;

      try {
        await copyToClipboard(urlText);
        setCopiedState();
      } catch (error) {
        if (buttonLabel) buttonLabel.textContent = "Falhou";

        window.clearTimeout(resetTimer);
        resetTimer = window.setTimeout(() => {
          if (buttonLabel) buttonLabel.textContent = originalLabel;
        }, 2000);

        console.error("Não foi possível copiar a URL:", error);
      }
    });
  }
});
