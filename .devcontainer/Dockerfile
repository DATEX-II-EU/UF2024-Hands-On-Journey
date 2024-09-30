FROM mcr.microsoft.com/devcontainers/java:1-21-bullseye

# Install wget
RUN apt-get update \
    && apt-get install -y wget \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Install .NET 6
RUN wget https://dot.net/v1/dotnet-install.sh -O dotnet-install.sh \
    && chmod +x dotnet-install.sh \
    && ./dotnet-install.sh --channel 6.0 --install-dir /usr/share/dotnet \
    && rm dotnet-install.sh

# Install .NET 8
RUN wget https://dot.net/v1/dotnet-install.sh -O dotnet-install.sh \
    && chmod +x dotnet-install.sh \
    && ./dotnet-install.sh --channel 8.0 --install-dir /usr/share/dotnet \
    && rm dotnet-install.sh

# Add .NET to PATH for all users
ENV DOTNET_ROOT="/usr/share/dotnet"
ENV PATH="$DOTNET_ROOT:$PATH:/home/vscode/.dotnet/tools"

# Ensure .NET binaries are executable and accessible
RUN chmod -R a+rx /usr/share/dotnet

# Install svcutil as the vscode user
USER vscode
RUN dotnet tool install --global dotnet-svcutil

# Add global tools path to PATH for all users
RUN echo 'export PATH="$PATH:/home/vscode/.dotnet/tools"' >> /home/vscode/.bashrc

# Switch back to root user to install Python 3
USER root
RUN apt-get update \
    && apt-get install -y python3 python3-pip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
