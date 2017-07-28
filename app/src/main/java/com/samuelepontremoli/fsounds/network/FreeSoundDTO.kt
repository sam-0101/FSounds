package com.samuelepontremoli.fsounds.network

import com.google.gson.annotations.SerializedName

/**
* FSounds - Created by s.pontremoli on 24/07/2017.
*/
data class SearchResult(
        @SerializedName("count") val count: Int,
        @SerializedName("next") val next: String,
        @SerializedName("results") val results: List<SoundPartialDetail>,
        @SerializedName("previous") val previous: String
)

data class SoundPartialDetail(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("tags") val tags: List<String>,
        @SerializedName("license") val license: String,
        @SerializedName("username") val username: String
)

data class SoundDetail(
        @SerializedName("id") val id: Int,
        @SerializedName("url") val url: String,
        @SerializedName("name") val name: String,
        @SerializedName("tags") val tags: MutableList<String>,
        @SerializedName("description") val description: String,
        @SerializedName("geotag") val geotag: String,
        @SerializedName("created") val created: String,
        @SerializedName("license") val license: String,
        @SerializedName("type") val type: String,
        @SerializedName("channels") val channels: Int,
        @SerializedName("filesize") val filesize: Int,
        @SerializedName("bitrate") val bitrate: Int,
        @SerializedName("bitdepth") val bitdepth: Int,
        @SerializedName("duration") val duration: Double,
        @SerializedName("samplerate") val samplerate: Double,
        @SerializedName("username") val username: String,
        @SerializedName("pack") val pack: String,
        @SerializedName("pack_name") val packName: String,
        @SerializedName("download") val download: String,
        @SerializedName("bookmark") val bookmark: String,
        @SerializedName("previews") val previews: SoundPreviews,
        @SerializedName("images") val images: SoundImages,
        @SerializedName("num_downloads") val numDownloads: Int,
        @SerializedName("avg_rating") val avgRating: Double,
        @SerializedName("num_ratings") val numRatings: Int,
        @SerializedName("rate") val rate: String,
        @SerializedName("comments") val comments: String,
        @SerializedName("num_comments") val numComments: Int,
        @SerializedName("comment") val comment: String,
        @SerializedName("similar_sounds") val similarSounds: String,
        @SerializedName("analysis") val analysis: String,
        @SerializedName("analysis_frames") val analysisFrames: String,
        @SerializedName("analysis_stats") val analysisStats: String
)

data class SoundImages(
        @SerializedName("waveform_l") val waveformL: String,
        @SerializedName("waveform_m") val waveformM: String,
        @SerializedName("spectral_m") val spectralM: String,
        @SerializedName("spectral_l") val spectralL: String
)

data class SoundPreviews(
        @SerializedName("preview-lq-ogg") val previewLqOgg: String,
        @SerializedName("preview-lq-mp3") val previewLqMp3: String,
        @SerializedName("preview-hq-ogg") val previewHqOgg: String,
        @SerializedName("preview-hq-mp3") val previewHqMp3: String
)
