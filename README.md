# Spring Into MCP

A demonstration MCP (Model Context Protocol) server built with Spring AI that provides information about KCDC (Kansas City Developer Conference) sessions and speakers.

## What is MCP?

Model Context Protocol (MCP) is a standardized way to connect AI models to external data sources and tools. Think of it as a bridge that allows Claude (and other AI models) to access real-time information and perform actions beyond their training data.

This demo server shows how to build an MCP server using Spring AI that exposes:
- **Tools**: Functions Claude can call to fetch data
- **Prompts**: Pre-built prompt templates
- **Resources**: Static content that can be retrieved

## Features

This MCP server provides three main capabilities:

### Tools
- `kcdc-sessions-by-date`: Get session counts grouped by conference date
- `kcdc-sessions`: Retrieve all KCDC 2025 sessions with details

### Prompts
- Conference-specific prompt templates for session recommendations

### Resources
- Speaker information and backgrounds

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.6+

### Build and Run
```bash
# Build the project
mvn clean package -DskipTests

# Run the server
java -jar target/kcdc-mcp-0.0.1-SNAPSHOT.jar
```

### Test the Server
```bash
# Use the MCP Inspector to test your server
npx @modelcontextprotocol/inspector
```


## Usage with Claude Desktop

### Configuration

Add this server to your Claude Desktop configuration file:

**Location**:
- macOS: `~/Library/Application Support/Claude/claude_desktop_config.json`
- Windows: `%APPDATA%\Claude\claude_desktop_config.json`

```json
{
  "mcpServers": {
    "kcdc-conference": {
      "command": "java",
      "args": [
        "-jar",
        "/Users/vega/Downloads/kcdc-mcp/target/kcdc-mcp-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}
```

**Note**: Update the path to match your actual project location.

### Example Queries

Once configured, you can ask Claude questions like:

- "How many sessions will there be at KCDC this year?"
- "Can you recommend sessions for a Java Developer?"
- "What sessions are available for someone interested in AI and MCP?"
- "What's Dan Vega's background and expertise for this conference?"

Claude will automatically use the MCP server's tools and resources to provide accurate, up-to-date answers.

## Project Structure

```
src/main/java/dev/danvega/kcdc/
├── Application.java          # Main Spring Boot application with MCP configuration
├── Conference.java           # Conference data model
├── Session.java             # Session data model
├── SessionTools.java        # MCP tools for session queries
├── ConferencePrompts.java   # MCP prompt templates
└── SpeakersResource.java    # MCP resource for speaker information

src/main/resources/
└── data/
    └── sessions.json        # Conference session data
```

### Key Components

- **SessionTools**: Provides callable functions for session data retrieval
- **ConferencePrompts**: Templates for common conference-related queries
- **SpeakersResource**: Static resource containing speaker information
- **sessions.json**: Contains all KCDC 2025 session data that the tools query

## Public MCP Server (HTTP + SSE)

By default, this MCP server runs in STDIO mode for direct integration with Claude Desktop. However, you can also run it as a public HTTP server using Server-Sent Events (SSE) for broader accessibility.

### Configuration

The project includes two configuration profiles:

**Default (STDIO)**: `application.properties`
- Uses standard input/output for communication
- Perfect for Claude Desktop integration
- No network exposure

**Public (HTTP + SSE)**: `application-sse.yaml`
- Runs as a web server on port 8080
- Uses Server-Sent Events for real-time communication
- Accessible over HTTP for remote clients

### Running as Public Server

```bash
# Build the project
mvn clean package -DskipTests

# Run with SSE profile
java -jar target/kcdc-mcp-0.0.1-SNAPSHOT.jar --spring.profiles.active=sse
```

### Testing the Public Server

Once running, you can test the MCP server endpoints:

```bash
# Check server status
curl http://localhost:8080/mcp/status

# List available tools
curl http://localhost:8080/mcp/tools

# Test a tool call
curl -X POST http://localhost:8080/mcp/tools/kcdc-sessions-by-date \
  -H "Content-Type: application/json"
```

This allows the MCP server to be deployed publicly and accessed by any MCP-compatible client over HTTP.

## Agenda

- Slides:
  - 
- Demo Start Here:
  - Browser Tabs:
    - https://claude.ai/new
    - https://start.spring.io/
    - https://docs.spring.io/spring-ai/reference/index.html
    - https://modelcontextprotocol.io/docs/getting-started/intro
- Ask claude (web or desktop) how many sessions are at KCDC this year
  - The model wasn't trained on this data
  - It will now invoke tools to try gather the data it needs to answer that question
- Create the MCP Server
  - [start.spring.io](http://start.spring.io)
  - Web, MCP Server
  - application.properties
  - Show sessions data
- Models
  - Conference
  - Sessions
- Tools
  - SessionTools
  - Discuss Additional tools but don't implement
  - Add tool callbacks
  - Build `mvn clean package -DskipTests`
  - Test `npx @modelcontextprotocol/inspector`
  - Finished at 14:30
- Prompts
  - Conference Prompts
  - Build & Test
  - Finished at 20:00
- Resources
  - SpeakerResource
  - Build & Test
  - Finished at 25:00
- Claude Desktop
  - [Configuration](#configuration)
  - Demo Prompts
    - How many sessions will there be at KCDC this year?
    - I'm a Java developer interested in AI and MCP. Can you recommend any sessions at KCDC this year for me?
    - That session with Dan Vega sounds interesting, can you give me some background information on him?
  - Prompts
  - Resources
  - Finished 32
- HTTP + SSE
  - Turning this into a public MCP server
  - Finished at 34:00