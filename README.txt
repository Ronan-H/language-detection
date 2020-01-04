
== Package breakdown & Design patterns ==
ie.gmit...
    language_detector_system: classes relating to the queueing and processing of language detection jobs
        (where LangDetectionSystem behaves as a facade)
    language_detector: classes relating to language detection algorithms
        (using the strategy pattern)
        (where LangDetectors can be easily constructed using the factory pattern used by LangDetectorPattern)
    language_distribution: classes relating to the storage of k-mer frequency data, and the recording of k-mer occurrences
        (where LangDistStoreBuilder, a builder pattern, can be used to make the construction of these stores easier)
    sample_parser: classes relating to the parsing of language samples (pairs of language text and language name)

== Design choices ==
- All areas of the project are loosely coupled and make use of abstractions and interfaces where possible, allowing easy
extension or modification of the codebase (e.g., extending SampleParser to allow language samples to be read from a webpage).

- Instead of using TreeMaps, I decided to try out using arrays to record k-mer occurrences, where the k-mer is hashed to a
value within the range of a large int array. I was hoping that collisions wouldn't affect the accuracy of the language
detection very much, and it seems to work just fine.

- Language detection is done using the strategy pattern, allowing the language detection algorithm to be easily swapped
at runtime.

- Language detection jobs are processed asynchronously by a number of workers, on their own thread. BlockingQueues are used,
allowing a large number of jobs to be queued and processed seamlessly.

== EXTRAS ==
- 3 separate language detection metrics implemented, which the user can choose between at runtime (from the options drop-down):
    "Out-of-place"
    "Simple distance"
    "Cosine similarity" (PERFORMS BEST)