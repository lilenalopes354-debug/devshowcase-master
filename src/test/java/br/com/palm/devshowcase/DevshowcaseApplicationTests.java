package br.com.palm.devshowcase;

import br.com.palm.devshowcase.repository.FeedbackRepository;
import br.com.palm.devshowcase.repository.ProfileRepository;
import br.com.palm.devshowcase.repository.ProjectRepository;
import br.com.palm.devshowcase.repository.TechnologyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DevshowcaseApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private TechnologyRepository technologyRepository;

	@BeforeEach
	void limparBanco() {
		feedbackRepository.deleteAll();
		projectRepository.deleteAll();
		technologyRepository.deleteAll();
		profileRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void deveExecutarFluxoPrincipalDaApi() throws Exception {
		String profileResponse = mockMvc.perform(post("/api/profiles")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"name":"Ada Lovelace","email":"ada@example.com","bio":"Backend developer","githubUrl":"https://github.com/ada","linkedinUrl":"https://linkedin.com/in/ada"}
					"""))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value("Ada Lovelace"))
			.andReturn().getResponse().getContentAsString();

		JsonNode profile = objectMapper.readTree(profileResponse);
		long profileId = profile.get("id").asLong();

		String technologyResponse = mockMvc.perform(post("/api/technologies")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Java\"}"))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value("Java"))
			.andReturn().getResponse().getContentAsString();

		long technologyId = objectMapper.readTree(technologyResponse).get("id").asLong();

		String projectResponse = mockMvc.perform(post("/api/projects")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"title":"DevShowcase API","description":"Portfolio API","projectUrl":"https://github.com/ada/devshowcase","profileId":%d,"technologyIds":[%d]}
					""".formatted(profileId, technologyId)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.title").value("DevShowcase API"))
			.andExpect(jsonPath("$.technologies[0].name").value("Java"))
			.andReturn().getResponse().getContentAsString();

		long projectId = objectMapper.readTree(projectResponse).get("id").asLong();

		mockMvc.perform(get("/api/profiles/{id}", profileId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(profileId));

		mockMvc.perform(get("/api/projects"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").value(projectId));

		mockMvc.perform(get("/api/technologies"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").value(technologyId));
	}

	@Test
	void deveRejeitarCadastroDeProjetoInvalido() throws Exception {
		mockMvc.perform(post("/api/projects")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"\",\"projectUrl\":\"url-invalida\"}"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors.title").exists())
			.andExpect(jsonPath("$.errors.projectUrl").exists())
			.andExpect(jsonPath("$.errors.profileId").exists());
	}
}
