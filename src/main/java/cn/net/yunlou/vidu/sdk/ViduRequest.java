package cn.net.yunlou.vidu.sdk;

import java.io.Serializable;
import lombok.Data;

/**

 * img2video
 "model": "viduq1",
 "images": ["https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/image2video.png"],
 "prompt": "The astronaut waved and the camera moved up.",
 "duration": 5,
 "seed": 0,
 "resolution": "1080p",
 "movement_amplitude": "auto",
 "bgm": false,
 "payload": false,
 "off_peak": false
 "callback_url": ""



 start-end2video
 "model": "viduq1",
 "images": ["https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/startend2video-1.jpeg","https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/startend2video-2.jpeg"],
 "prompt": "The camera zooms in on the bird, which then flies to the right. With its flight being smooth and natural, the bird soars in the sky. with a red light effect following and surrounding it from behind.",
 "duration": 5,
 "seed": 0,
 "resolution": "1080p",
 "movement_amplitude": "auto",
 "off_peak": false

 text2video
 "model": "viduq1",
 "style": "general",
 "prompt": "In an ultra-realistic fashion photography style featuring light blue and pale amber tones, an astronaut in a spacesuit walks through the fog. The background consists of enchanting white and golden lights, creating a minimalist still life and an impressive panoramic scene.",
 "duration": 5,
 "seed": 0,
 "aspect_ratio": "16:9",
 "resolution": "1080p",
 "movement_amplitude": "auto",
 "off_peak": false

 template2video
 "template": "hugging",
 "images": ["https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/scene-template/hug.jpeg"],
 "prompt": "Video content\\n画面中的两个主体转向彼此，并开始拥抱# 要求\\n将Motion Level设置为‘Large’",
 "seed": 0

 template-story
 "story": "choose_one_accept_value",
 "images":["input_your_images"]

 upscale-new
 "video_url": "your_video_url",
 "upscale_resolution": "1080p"

 lip-sync
 "video_url": "your_video_url",
 "audio_url": "your_audio_url"

 text2audio
 "model": "audio1.0",
 "prompt": "清晨的鸟叫声",
 "duration": 10,
 "seed": 0

 timing2audio
 "model": "audio1.0",
 "duration": 10,
 "timing_prompts": [
 {
 "from": 0.0,
 "to": 3.0,
 "prompt": "清晨的鸟叫声"
 },
 {
 "from": 3.0,
 "to": 6.0,
 "prompt": "远处传来火车驶过的声音"
 },
 {
 "from": 5.0,
 "to": 9.5,
 "prompt": "海浪轻轻拍打沙滩"
 }
 ],
 "seed": 0

 */
@Data
public class ViduRequest<T> implements Serializable {


}
