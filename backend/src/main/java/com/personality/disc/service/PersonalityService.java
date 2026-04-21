package com.personality.disc.service;

import com.personality.disc.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonalityService {

    private static final String LEON = "LEON";
    private static final String NUTRIA = "NUTRIA";
    private static final String GOLDEN = "GOLDEN";
    private static final String CASTOR = "CASTOR";

    private final Map<String, PersonalityInfo> personalities = new LinkedHashMap<>();

    public PersonalityService() {
        initPersonalities();
    }

    private void initPersonalities() {
        PersonalityInfo leon = new PersonalityInfo();
        leon.setKey(LEON);
        leon.setName("León");
        leon.setEmoji("lion");
        leon.setKeyword("Emprendedor");
        leon.setDescription("Personalidad fuerte. Es la persona que siempre toma la iniciativa, altamente activo, le gusta hacer las cosas bien. Es líder, le gusta el mando.");
        leon.setNegatives(List.of("Inspira temor", "Impaciente", "Crítico", "Presiona a los demás"));
        leon.setPositives(List.of("Ganador", "Líder nato", "Motiva a otros", "Toma la iniciativa"));
        leon.setNeeds(List.of(
            "Mucha estructura y supervisión",
            "Oportunidades para convertirse en líder",
            "Puestos con responsabilidad para sentirse motivado"
        ));
        personalities.put(LEON, leon);

        PersonalityInfo nutria = new PersonalityInfo();
        nutria.setKey(NUTRIA);
        nutria.setName("Nutria");
        nutria.setEmoji("otter");
        nutria.setKeyword("Comunicativo");
        nutria.setDescription("Personalidad inquieta. Persona que les gustan mucho las relaciones interpersonales, se relaciona muy bien con todo el mundo. Impulsivo y platicador. Creativo, lleno de proyectos.");
        nutria.setNegatives(List.of("Impulsivo", "Agresivo verbalmente", "Demasiado confiado", "Decisiones superficiales"));
        nutria.setPositives(List.of("Motivador", "Creativo", "Se adapta a los cambios", "Muy sociable", "Facilidad de palabra", "Alegre"));
        nutria.setNeeds(List.of(
            "Que se les ayude a mantener la concentración",
            "Variedad en sus actividades",
            "Organizarse, conocer sus límites",
            "Medir riesgos y consecuencias"
        ));
        personalities.put(NUTRIA, nutria);

        PersonalityInfo golden = new PersonalityInfo();
        golden.setKey(GOLDEN);
        golden.setName("Labrador Dorado");
        golden.setEmoji("dog");
        golden.setKeyword("Reservado");
        golden.setDescription("Personalidad pacífica. Es la persona que suele aislarse, poco comunicativo, gran pensadora. Se toma su tiempo antes de actuar. Necesita confianza para poder actuar.");
        golden.setNegatives(List.of("Dependiente", "Se envuelve en detalles", "Cede posición para evitar enfrentamiento"));
        golden.setPositives(List.of("Comprensivo", "Abierto", "Sociable", "Se interesa por los demás", "Leal", "Mediador"));
        golden.setNeeds(List.of(
            "Mucha comprensión",
            "Tiempo en sus tareas",
            "Sentir que no están solos",
            "Que se les escuche y entiendan sus sentimientos"
        ));
        personalities.put(GOLDEN, golden);

        PersonalityInfo castor = new PersonalityInfo();
        castor.setKey(CASTOR);
        castor.setName("Castor");
        castor.setEmoji("beaver");
        castor.setKeyword("Ordenado");
        castor.setDescription("Personalidad disciplinada, le gusta llevar un horario en todo. Organiza sus actividades diarias y no acepta cambios fácilmente. Metódica. Se relaciona poco con los demás. Altamente organizado.");
        castor.setNegatives(List.of("Se tarda para ajustarse a los cambios", "Espera órdenes antes de actuar", "Necesita guía y dirección confiable"));
        castor.setPositives(List.of("Muy organizado", "Metódico", "Planificado", "Práctico", "Preciso", "Responsable", "Termina todo lo que empieza"));
        castor.setNeeds(List.of(
            "Rutinas fijas",
            "Que se les avisen los cambios con tiempo",
            "Inspirarles seguridad en las rutinas frecuentes",
            "Sentirse queridos",
            "Actividades especiales en momentos especiales"
        ));
        personalities.put(CASTOR, castor);
    }

    public List<QuestionRound> getQuestions() {
        List<QuestionRound> rounds = new ArrayList<>();

        // Round 1
        rounds.add(new QuestionRound(1, List.of(
            new QuestionOption(LEON, "¿Te gusta el poder?"),
            new QuestionOption(NUTRIA, "¿Eres entusiasta?"),
            new QuestionOption(GOLDEN, "¿Eres sentimental?"),
            new QuestionOption(CASTOR, "¿Necesitas instrucciones precisas?")
        )));

        // Round 2
        rounds.add(new QuestionRound(2, List.of(
            new QuestionOption(LEON, "¿Necesitas tomar el mando?"),
            new QuestionOption(NUTRIA, "¿Te gusta arriesgarte?"),
            new QuestionOption(GOLDEN, "¿Eres leal?"),
            new QuestionOption(CASTOR, "¿Te gusta la exactitud?")
        )));

        // Round 3
        rounds.add(new QuestionRound(3, List.of(
            new QuestionOption(LEON, "¿Eres firme y directo?"),
            new QuestionOption(NUTRIA, "¿Eres visionario/emprendedor?"),
            new QuestionOption(GOLDEN, "¿Eres calmado y pacífico?"),
            new QuestionOption(CASTOR, "¿Eres consistente?")
        )));

        // Round 4
        rounds.add(new QuestionRound(4, List.of(
            new QuestionOption(LEON, "¿Eres emprendedor?"),
            new QuestionOption(NUTRIA, "¿Te encanta platicar?"),
            new QuestionOption(GOLDEN, "¿Te gusta la rutina?"),
            new QuestionOption(CASTOR, "¿Eres predecible?")
        )));

        // Round 5
        rounds.add(new QuestionRound(5, List.of(
            new QuestionOption(LEON, "¿Eres competitivo?"),
            new QuestionOption(NUTRIA, "¿Eres promotor?"),
            new QuestionOption(GOLDEN, "¿No te gusta el cambio?"),
            new QuestionOption(CASTOR, "¿Eres justo?")
        )));

        // Round 6
        rounds.add(new QuestionRound(6, List.of(
            new QuestionOption(LEON, "¿Resuelves los problemas?"),
            new QuestionOption(NUTRIA, "¿Eres popular?"),
            new QuestionOption(GOLDEN, "¿Eres voluntarioso?"),
            new QuestionOption(CASTOR, "¿Eres objetivo?")
        )));

        // Round 7
        rounds.add(new QuestionRound(7, List.of(
            new QuestionOption(LEON, "¿Eres productivo?"),
            new QuestionOption(NUTRIA, "¿Eres divertido?"),
            new QuestionOption(GOLDEN, "¿Evitas las peleas?"),
            new QuestionOption(CASTOR, "¿Eres consciente y detallista?")
        )));

        // Round 8
        rounds.add(new QuestionRound(8, List.of(
            new QuestionOption(LEON, "¿Eres valiente?"),
            new QuestionOption(NUTRIA, "¿Te gusta la diversidad?"),
            new QuestionOption(GOLDEN, "¿Eres compasivo?"),
            new QuestionOption(CASTOR, "¿Eres perfeccionista?")
        )));

        // Round 9
        rounds.add(new QuestionRound(9, List.of(
            new QuestionOption(LEON, "¿Tomas decisiones rápido?"),
            new QuestionOption(NUTRIA, "¿Eres espontáneo?"),
            new QuestionOption(GOLDEN, "¿Proteges a los demás?"),
            new QuestionOption(CASTOR, "¿Eres meticuloso?")
        )));

        // Round 10
        rounds.add(new QuestionRound(10, List.of(
            new QuestionOption(LEON, "¿Eres persistente?"),
            new QuestionOption(NUTRIA, "¿Eres alentador?"),
            new QuestionOption(GOLDEN, "¿Eres pacificador?"),
            new QuestionOption(CASTOR, "¿Eres analítico?")
        )));

        return rounds;
    }

    public TestResult evaluate(TestAnswers answers) {
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put(LEON, 0);
        scores.put(NUTRIA, 0);
        scores.put(GOLDEN, 0);
        scores.put(CASTOR, 0);

        for (Map<String, Integer> round : answers.getRounds()) {
            for (Map.Entry<String, Integer> entry : round.entrySet()) {
                scores.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }

        List<PersonalityRank> ranking = scores.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .map(entry -> {
                PersonalityInfo info = personalities.get(entry.getKey());
                return new PersonalityRank(0, entry.getKey(), info.getName(), info.getEmoji(), entry.getValue());
            })
            .collect(Collectors.toList());

        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosition(i + 1);
        }

        TestResult result = new TestResult();
        result.setScores(scores);
        result.setRanking(ranking);
        result.setDominantPersonality(personalities.get(ranking.get(0).getKey()));

        return result;
    }

    public List<PersonalityInfo> getAllPersonalities() {
        return new ArrayList<>(personalities.values());
    }

    public PersonalityInfo getPersonality(String key) {
        return personalities.get(key.toUpperCase());
    }
}
